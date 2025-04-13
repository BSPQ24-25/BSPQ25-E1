package com.hospital.portal.unit.TestServices;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.hospital.portal.service.PasswordService;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private AdminRepository adminRepository;  // Mock for AdminRepository

    @Mock
    private DoctorRepository doctorRepository;  // Mock for DoctorRepository

    @Mock
    private PatientRepository patientRepository;  // Mock for PatientRepository

    @Mock
    private PasswordService passwordService;  // Mock for PasswordService

    @InjectMocks
    private LoginService loginService;  // Inject the mocks into LoginService

    // Set up method to initialize mocks before each test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initializes the mocks
    }

    // Test for successful login as an Admin
    @Test
    void testLoginAsAdmin_Success() {
        String dni = "12345678X";
        String password = "adminpass";
        Admin admin = new Admin();
        admin.setDni(dni);
        admin.setName("Admin Name");
        admin.setPassword(password);

        // Mock finding the admin in the repository and verify password
        when(adminRepository.findById(dni)).thenReturn(Optional.of(admin));
        when(passwordService.verifyPassword(password, admin.getPassword())).thenReturn(true);

        // Call the login method
        Map<String, Object> result = loginService.login(dni, password);

        // Verify the expected result for an Admin
        assertEquals("ADMIN", result.get("role"));
        assertEquals(dni, result.get("dni"));
        assertEquals("Admin Name", result.get("name"));
        assertTrue(result.containsKey("token"));  // Verify that a token is generated
    }

    // Test for successful login as a Doctor
    @Test
    void testLoginAsDoctor_Success() {
        String dni = "87654321X";
        String password = "doctorpass";
        Doctor doctor = new Doctor();
        doctor.setDni(dni);
        doctor.setName("Doctor Name");
        doctor.setPassword(password);

        // Mock finding the doctor in the repository and verify password
        when(adminRepository.findById(dni)).thenReturn(Optional.empty());  // No admin found
        when(doctorRepository.findById(dni)).thenReturn(Optional.of(doctor));
        when(passwordService.verifyPassword(password, doctor.getPassword())).thenReturn(true);

        // Call the login method
        Map<String, Object> result = loginService.login(dni, password);

        // Verify the expected result for a Doctor
        assertEquals("DOCTOR", result.get("role"));
        assertEquals(dni, result.get("dni"));
        assertEquals("Doctor Name", result.get("name"));
        assertTrue(result.containsKey("token"));  // Verify that a token is generated
    }

    // Test for successful login as a Patient
    @Test
    void testLoginAsPatient_Success() {
        String dni = "11223344X";
        String password = "patientpass";
        Patient patient = new Patient();
        patient.setDni(dni);
        patient.setName("Patient Name");
        patient.setPassword(password);

        // Mock finding the patient in the repository and verify password
        when(adminRepository.findById(dni)).thenReturn(Optional.empty());  // No admin found
        when(doctorRepository.findById(dni)).thenReturn(Optional.empty());  // No doctor found
        when(patientRepository.findById(dni)).thenReturn(Optional.of(patient));
        when(passwordService.verifyPassword(password, patient.getPassword())).thenReturn(true);

        // Call the login method
        Map<String, Object> result = loginService.login(dni, password);

        // Verify the expected result for a Patient
        assertEquals("PATIENT", result.get("role"));
        assertEquals(dni, result.get("dni"));
        assertEquals("Patient Name", result.get("name"));
        assertTrue(result.containsKey("token"));  // Verify that a token is generated
    }

    // Test for login with invalid credentials (DNI not found in any repository)
    @Test
    void testLogin_InvalidCredentials() {
        String dni = "00000000X";
        String password = "wrongpass";

        // Mock no user found in any repository
        when(adminRepository.findById(dni)).thenReturn(Optional.empty());
        when(doctorRepository.findById(dni)).thenReturn(Optional.empty());
        when(patientRepository.findById(dni)).thenReturn(Optional.empty());

        // Call the login method
        Map<String, Object> result = loginService.login(dni, password);

        // Verify that the result indicates invalid credentials
        assertEquals("INVALID_CREDENTIALS", result.get("role"));
    }

    // Test for login with a wrong password
    @Test
    void testLogin_WrongPassword() {
        String dni = "99999999X";
        String password = "correctpass";
        Admin admin = new Admin();
        admin.setDni(dni);
        admin.setName("Admin Name");
        admin.setPassword("differentpass");  // The stored password is different

        // Mock finding the admin and verify incorrect password
        when(adminRepository.findById(dni)).thenReturn(Optional.of(admin));
        when(passwordService.verifyPassword(password, admin.getPassword())).thenReturn(false);

        // Call the login method
        Map<String, Object> result = loginService.login(dni, password);

        // Verify that the result indicates invalid credentials
        assertEquals("INVALID_CREDENTIALS", result.get("role"));
    }
}
