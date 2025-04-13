package com.hospital.portal.unit.TestServices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.hospital.portal.repository.AdminRepository;
import com.hospital.portal.repository.DoctorRepository;
import com.hospital.portal.repository.PatientRepository;
import com.hospital.portal.service.RoleService;

class RoleServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private RoleService roleService;

    // We initialize the mocks in the setup

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test to confirm that if it is an admin, it will return the ADMIN string

    @Test
    void testDetermineUserRole_Admin() {
        String dni = "12345678A";
        when(adminRepository.existsById(dni)).thenReturn(true);

        String role = roleService.determineUserRole(dni);

        assertEquals("ADMIN", role);
    }

    // Test to confirm that if it is an doctor, it will return the DOCTOR string
    @Test
    void testDetermineUserRole_Doctor() {
        String dni = "87654321B";
        when(adminRepository.existsById(dni)).thenReturn(false);
        when(doctorRepository.existsById(dni)).thenReturn(true);

        String role = roleService.determineUserRole(dni);

        assertEquals("DOCTOR", role);
    }

    // Test to confirm that if it is an patient, it will return the PATIENT string

    @Test
    void testDetermineUserRole_Patient() {
        String dni = "11223344C";
        when(adminRepository.existsById(dni)).thenReturn(false);
        when(doctorRepository.existsById(dni)).thenReturn(false);
        when(patientRepository.existsById(dni)).thenReturn(true);

        String role = roleService.determineUserRole(dni);

        assertEquals("PATIENT", role);
    }

    // Test to confirm that if it is not valid, it will return the NULL

    @Test
    void testDetermineUserRole_NotFound() {
        String dni = "99999999X";
        when(adminRepository.existsById(dni)).thenReturn(false);
        when(doctorRepository.existsById(dni)).thenReturn(false);
        when(patientRepository.existsById(dni)).thenReturn(false);

        String role = roleService.determineUserRole(dni);

        assertNull(role);
    }
}
