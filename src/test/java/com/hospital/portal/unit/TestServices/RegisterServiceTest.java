package com.hospital.portal.unit.TestServices;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.PatientRepository;
import com.hospital.portal.service.PasswordService;
import com.hospital.portal.service.RegisterService;

class RegisterServiceTest {

    @Mock
    private PatientRepository patientRepository;  // Mock for PatientRepository

    @Mock
    private PasswordService passwordService;  // Mock for PasswordService

    @InjectMocks
    private RegisterService registerService;  // Inject the mocks into RegisterService

    // Set up method to initialize mocks before each test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initializes the mocks
    }

    // Test to verify that if no patients exist, the first patient will be assigned ID "P0001"
    @Test
    void testGenerateIncrementalPatientId_FirstPatient() {
        // Mock the database to simulate no patients
        when(patientRepository.findTopByOrderByPatientIdDesc()).thenReturn(Optional.empty());

        // Call the method to generate the new patient ID
        String newPatientId = registerService.generateIncrementalPatientId();

        // Verify the expected result
        assertEquals("P0001", newPatientId);  // The first patient ID should be "P0001"
    }

    // Test to verify that if there are 23 existing patients, the next one will be assigned ID "P0024"
    @Test
    void testGenerateIncrementalPatientId_WithExistingPatients() {
        // Simulate the last patient having ID "P0023"
        Patient lastPatient = new Patient();
        lastPatient.setPatientId("P0023");
        when(patientRepository.findTopByOrderByPatientIdDesc()).thenReturn(Optional.of(lastPatient));

        // Call the method to generate the new patient ID
        String newPatientId = registerService.generateIncrementalPatientId();

        // Verify that the new ID will be "P0024"
        assertEquals("P0024", newPatientId);  // The new ID should be "P0024"
    }

    // Test for successful patient registration
    @Test
    public void testRegisterPatient_Success() {
        // Patient data for registration
        String dni = "12345678X";
        String name = "John";
        String surname = "Doe";
        String phone = "123456789";
        String email = "john@example.com";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        String gender = "Male";
        String password = "password123";

        // Mock checks for the availability of the DNI and email
        when(patientRepository.existsByDni(dni)).thenReturn(false);  // Mocking that the DNI does not exist
        when(patientRepository.existsByMail(email)).thenReturn(false);  // Mocking that the email does not exist
        when(patientRepository.findTopByOrderByPatientIdDesc()).thenReturn(Optional.empty());  // Mocking no existing patients

        // Call the method to register the patient
        String result = registerService.registerPatient(dni, name, surname, phone, email, birthDate, gender, password);

        // Verify the expected result
        assertEquals("Registration successful", result);  // The registration should be successful

        // Verify that saveAndFlush method was called on the repository
        verify(patientRepository).saveAndFlush(any(Patient.class));  // Verifying that the patient is saved
    }
}
