package com.hospital.portal.TestServices;
import java.util.Map;
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

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginAsAdmin_Success() {
        String dni = "12345678X";
        String password = "adminpass";
        Admin admin = new Admin();
        admin.setDni(dni);
        admin.setName("Admin Name");
        admin.setPassword(password);

        when(adminRepository.findById(dni)).thenReturn(Optional.of(admin));

        Map<String, Object> result = loginService.login(dni, password);

        assertEquals("ADMIN", result.get("role"));
        assertEquals(dni, result.get("dni"));
        assertEquals("Admin Name", result.get("name"));
    }

    @Test
    void testLoginAsDoctor_Success() {
        String dni = "87654321X";
        String password = "doctorpass";
        Doctor doctor = new Doctor();
        doctor.setDni(dni);
        doctor.setName("Doctor Name");
        doctor.setPassword(password);

        when(adminRepository.findById(dni)).thenReturn(Optional.empty());
        when(doctorRepository.findById(dni)).thenReturn(Optional.of(doctor));

        Map<String, Object> result = loginService.login(dni, password);

        assertEquals("DOCTOR", result.get("role"));
        assertEquals(dni, result.get("dni"));
        assertEquals("Doctor Name", result.get("name"));
    }

    @Test
    void testLoginAsPatient_Success() {
        String dni = "11223344X";
        String password = "patientpass";
        Patient patient = new Patient();
        patient.setDni(dni);
        patient.setName("Patient Name");
        patient.setPassword(password);

        when(adminRepository.findById(dni)).thenReturn(Optional.empty());
        when(doctorRepository.findById(dni)).thenReturn(Optional.empty());
        when(patientRepository.findById(dni)).thenReturn(Optional.of(patient));

        Map<String, Object> result = loginService.login(dni, password);

        assertEquals("PATIENT", result.get("role"));
        assertEquals(dni, result.get("dni"));
        assertEquals("Patient Name", result.get("name"));
    }

    @Test
    void testLogin_InvalidCredentials() {
        String dni = "00000000X";
        String password = "wrongpass";

        when(adminRepository.findById(dni)).thenReturn(Optional.empty());
        when(doctorRepository.findById(dni)).thenReturn(Optional.empty());
        when(patientRepository.findById(dni)).thenReturn(Optional.empty());

        Map<String, Object> result = loginService.login(dni, password);

        assertEquals("INVALID_CREDENTIALS", result.get("role"));
    }

    @Test
    void testLogin_WrongPassword() {
        String dni = "99999999X";
        String password = "correctpass";
        Admin admin = new Admin();
        admin.setDni(dni);
        admin.setName("Admin Name");
        admin.setPassword("differentpass");

        when(adminRepository.findById(dni)).thenReturn(Optional.of(admin));

        Map<String, Object> result = loginService.login(dni, password);

        assertEquals("INVALID_CREDENTIALS", result.get("role"));
    }
}