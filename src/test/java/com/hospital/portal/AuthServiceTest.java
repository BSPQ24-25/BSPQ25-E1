package com.hospital.portal;

import com.hospital.portal.dto.LoginRequest;
import com.hospital.portal.dto.PatientRegistrationRequest;
import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.PatientRepository;
import com.hospital.portal.service.AuthService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    public void registerPatient_NewPatient_ReturnsPatient() {
        PatientRegistrationRequest request = new PatientRegistrationRequest();
        request.setDni("12345678A");
        request.setName("John");
        request.setPassword("password123");

        when(patientRepository.existsById(request.getDni())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword");
        
        Patient savedPatient = new Patient();
        savedPatient.setId(request.getDni());
        when(patientRepository.save(any(Patient.class))).thenReturn(savedPatient);

        Patient result = authService.registerPatient(request);

        assertNotNull(result);
        assertEquals(request.getDni(), result.getId());
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    public void registerPatient_ExistingPatient_ThrowsException() {
        PatientRegistrationRequest request = new PatientRegistrationRequest();
        request.setDni("12345678A");

        when(patientRepository.existsById(request.getDni())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authService.registerPatient(request));
    }

    @Test
    public void authenticate_ValidPatientCredentials_ReturnsPatient() {
        LoginRequest request = new LoginRequest();
        request.setDni("12345678A");
        request.setPassword("correctPassword");

        Patient patient = new Patient();
        patient.setId(request.getDni());
        patient.setPassword("hashedPassword");

        when(patientRepository.existsById(request.getDni())).thenReturn(true);
        when(patientRepository.findById(request.getDni())).thenReturn(java.util.Optional.of(patient));
        when(passwordEncoder.matches(request.getPassword(), patient.getPassword())).thenReturn(true);

        Object result = authService.authenticate(request);

        assertTrue(result instanceof Patient);
        assertEquals(request.getDni(), ((Patient) result).getId());
    }
}