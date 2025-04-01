package com.hospital.portal.TestServices;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.PatientRepository;
import com.hospital.portal.service.RegisterService;

class RegisterServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private RegisterService registerService;


    // Create the mocks in the setup

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test to check that if it is no patient created, the first one is P0001

    @Test
    void testGenerateIncrementalPatientId_FirstPatient() {
        // Simular que no hay pacientes en la BD
        when(patientRepository.findTopByOrderByPatientIdDesc()).thenReturn(Optional.empty());

        // Ejecutar el método
        String newPatientId = registerService.generateIncrementalPatientId();

        // Verificar resultado esperado
        assertEquals("P0001", newPatientId);
    }

     // Test to check that if there are 23 patient the next one is P0024


    @Test
    void testGenerateIncrementalPatientId_WithExistingPatients() {
        // Simular que el último paciente tiene ID "P0023"
        Patient lastPatient = new Patient();
        lastPatient.setPatientId("P0023");
        when(patientRepository.findTopByOrderByPatientIdDesc()).thenReturn(Optional.of(lastPatient));

        // Ejecutar el método
        String newPatientId = registerService.generateIncrementalPatientId();

        // Verificar que el nuevo ID sea "P0024"
        assertEquals("P0024", newPatientId);
    }

    //Test to check the register is done correctly

    @Test
    void testRegisterPatient_Success() {
        String dni = "12345678X";
        String name = "John";
        String surname = "Doe";
        String phone = "123456789";
        String email = "john@example.com";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        String gender = "Male";
        String password = "password123";

        // Simular que el paciente no está registrado
        when(patientRepository.existsById(dni)).thenReturn(false);
        when(patientRepository.findTopByOrderByPatientIdDesc()).thenReturn(Optional.empty());

        // Ejecutar el método
        String result = registerService.registerPatient(dni, name, surname, phone, email, birthDate, gender, password);

        // Verificar que el resultado sea el esperado
        assertEquals("Registration successful", result);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    //Test to check that if DNI is not valid, rejected 
    @Test
    void testRegisterPatient_DniAlreadyExists() {
        String dni = "12345678X";

        // Simular que el DNI ya está registrado
        when(patientRepository.existsById(dni)).thenReturn(true);

        // Ejecutar el método
        String result = registerService.registerPatient(dni, "John", "Doe", "123456789", "john@example.com",
                LocalDate.of(1990, 1, 1), "Male", "password123");

        // Verificar que se devuelve el mensaje esperado
        assertEquals("DNI already registered", result);
        verify(patientRepository, never()).save(any(Patient.class));
    }
}
