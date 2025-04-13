package com.hospital.portal.unit.TestModels;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.PatientRepository;

class PatientTest {

    private PatientRepository repository;
    private Patient patient;

    @BeforeEach
    void setUp() {
        // Create the Patient object
        patient = new Patient("1", "John", "Doe", LocalDate.of(1990, 4, 15), "Male", "987654321", "john.doe@mail.com", "password456", "patient001");

        // Mock the PatientRepository
        repository = mock(PatientRepository.class);
    }

    @Test
    @DisplayName("Should Add Patient Successfully")
    void testAddPatient() {
        // Simulate that the save method returns the patient object
        when(repository.save(patient)).thenReturn(patient);

        // Call the save method
        Patient result = repository.save(patient);

        // Verify that save was called once
        verify(repository, times(1)).save(patient);

        // Check that the saved patient has the correct patientId
        assertEquals("patient001", result.getPatientId());
    }

    @Test
    @DisplayName("Should Retrieve Patient By ID")
    void testGetPatientById() {
        // Simulate finding the patient with ID "1"
        when(repository.findById("1")).thenReturn(Optional.of(patient));

        // Get the patient from the repository
        Patient result = repository.findById("1").orElseThrow(() -> new RuntimeException("Patient not found"));

        // Verify that the retrieved patient is not null and has the correct name
        assertNotNull(result);
        assertEquals("John", result.getName());
    }

    @Test
    @DisplayName("Should Update Patient Patient ID")
    void testUpdatePatientPatientId() {
        // Change the patientId of the Patient
        patient.setPatientId("patient002");

        // Simulate that the save method updates the patient correctly
        when(repository.save(patient)).thenReturn(patient);

        // Update the patient
        Patient result = repository.save(patient);

        // Verify that the save method was called with the updated patient
        verify(repository, times(1)).save(patient);

        // Check that the patientId was updated correctly
        assertEquals("patient002", result.getPatientId());
    }

    @Test
    @DisplayName("Should Throw Exception When Patient Not Found")
    void testGetPatientById_NotFound() {
        // Simulate that the patient with ID "999" does not exist
        when(repository.findById("999")).thenReturn(Optional.empty());

        // Try to get a patient that doesn't exist
        Exception exception = assertThrows(RuntimeException.class, () -> {
            repository.findById("999").orElseThrow(() -> new RuntimeException("Patient not found"));
        });

        // Verify that the exception thrown is as expected
        assertEquals("Patient not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should Get and Set Patient ID Correctly")
    void testGetSetPatientId() {
        patient.setPatientId("patient003");
        assertEquals("patient003", patient.getPatientId());
    }
}
