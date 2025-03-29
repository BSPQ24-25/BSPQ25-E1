package com.hospital.portal.TestServices;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hospital.portal.model.Admin;
import com.hospital.portal.model.Doctor;
import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.AdminRepository;
import com.hospital.portal.repository.DoctorRepository;
import com.hospital.portal.repository.PatientRepository;
import com.hospital.portal.service.LoginService;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    // Create the mocks for the test

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private LoginService loginService;

    // We initialize all of them

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test to confirm that if it is an admin, it will return the ADMIN string

    @Test
    void testLoginAsAdmin_Success() {
        String dni = "12345678X";
        String password = "adminpass";
        Admin admin = new Admin();
        admin.setPassword(password);

        when(adminRepository.findById(dni)).thenReturn(Optional.of(admin));

        String result = loginService.login(dni, password);

        assertEquals("ADMIN", result);
    }

     // Test to confirm that if it is an doctor, it will return the DOCTOR string

    @Test
    void testLoginAsDoctor_Success() {
        String dni = "87654321X";
        String password = "doctorpass";
        Doctor doctor = new Doctor();
        doctor.setPassword(password);

        when(adminRepository.findById(dni)).thenReturn(Optional.empty());
        when(doctorRepository.findById(dni)).thenReturn(Optional.of(doctor));

        String result = loginService.login(dni, password);

        assertEquals("DOCTOR", result);
    }

     // Test to confirm that if it is an patient, it will return the PATIENT string

    @Test
    void testLoginAsPatient_Success() {
        String dni = "11223344X";
        String password = "patientpass";
        Patient patient = new Patient();
        patient.setPassword(password);

        when(adminRepository.findById(dni)).thenReturn(Optional.empty());
        when(doctorRepository.findById(dni)).thenReturn(Optional.empty());
        when(patientRepository.findById(dni)).thenReturn(Optional.of(patient));

        String result = loginService.login(dni, password);

        assertEquals("PATIENT", result);
    }

     // Test to confirm that if it is invalid, it will return the INVALID_CREDENTIALS string

    @Test
    void testLogin_InvalidCredentials() {
        String dni = "00000000X";
        String password = "wrongpass";

        when(adminRepository.findById(dni)).thenReturn(Optional.empty());
        when(doctorRepository.findById(dni)).thenReturn(Optional.empty());
        when(patientRepository.findById(dni)).thenReturn(Optional.empty());

        String result = loginService.login(dni, password);

        assertEquals("INVALID_CREDENTIALS", result);
    }

     // Test to confirm that if it is wrong password, it will return the INVALID_CREDENTIALS string

    @Test
    void testLogin_WrongPassword() {
        String dni = "99999999X";
        String password = "correctpass";
        Admin admin = new Admin();
        admin.setPassword("differentpass");

        when(adminRepository.findById(dni)).thenReturn(Optional.of(admin));

        String result = loginService.login(dni, password);

        assertEquals("INVALID_CREDENTIALS", result);
    }
}
