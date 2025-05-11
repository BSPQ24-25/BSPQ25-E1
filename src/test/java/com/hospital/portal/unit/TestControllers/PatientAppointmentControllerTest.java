package com.hospital.portal.unit.TestControllers;

import com.hospital.portal.controller.PatientAppointmentController;
import com.hospital.portal.model.Appointment;
import com.hospital.portal.service.PatientAppointmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PatientAppointmentControllerTest {

    @Mock
    private PatientAppointmentService patientAppointmentService;

    @InjectMocks
    private PatientAppointmentController patientAppointmentController;

    private Appointment appointment;

    @BeforeEach
    void setUp() {
        appointment = new Appointment(
            LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), null, null, "Routine Check"
        );
    }

    @Test
    void testGetAppointmentsByPatientDNI() {
        String dni = "pat001";
        List<Appointment> appointments = Arrays.asList(appointment);

        when(patientAppointmentService.getAppointmentsByPatientDNI(dni)).thenReturn(appointments);

        List<Appointment> result = patientAppointmentController.getAppointmentsByPatientDNI(dni);

        assertEquals(1, result.size());
        verify(patientAppointmentService, times(1)).getAppointmentsByPatientDNI(dni);
    }

    @Test
    void testGetAppointmentsByDate() {
        String dni = "pat001";
        LocalDate date = LocalDate.now();
        List<Appointment> appointments = Arrays.asList(appointment);

        when(patientAppointmentService.getAppointmentsByDate(dni, date)).thenReturn(appointments);

        List<Appointment> result = patientAppointmentController.getAppointmentsByDate(dni, date);

        assertEquals(1, result.size());
        verify(patientAppointmentService, times(1)).getAppointmentsByDate(dni, date);
    }

    @Test
    void testGetAppointmentsBySpecialty() {
        String dni = "pat001";
        String specialty = "Cardiology";
        List<Appointment> appointments = Arrays.asList(appointment);

        when(patientAppointmentService.getAppointmentByPatientDniAndDoctorSpecialtyName(dni, specialty)).thenReturn(appointments);

        List<Appointment> result = patientAppointmentController.getAppointmentByPatientDniAndDoctorSpecialtyName(dni, specialty);

        assertEquals(1, result.size());
        verify(patientAppointmentService, times(1)).getAppointmentByPatientDniAndDoctorSpecialtyName(dni, specialty);
    }

    @Test
    void testAddAppointmentPatient() {
        String dni = "pat001";

        when(patientAppointmentService.addAppointmentPatient(dni, appointment)).thenReturn(appointment);

        Appointment result = patientAppointmentController.addAppointmentPatient(dni, appointment);

        assertNotNull(result);
        assertEquals("Routine Check", result.getAppointmentPurpose());
        verify(patientAppointmentService, times(1)).addAppointmentPatient(dni, appointment);
    }

    @Test
    void testModifyAppointment() {
        String id = "apt123";
        Appointment modified = new Appointment(
            LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), null, null, "Updated Check-up"
        );

        when(patientAppointmentService.modifyAppointment(id, modified)).thenReturn(modified);

        Appointment result = patientAppointmentController.modifyAppointment(id, modified);

        assertNotNull(result);
        assertEquals("Updated Check-up", result.getAppointmentPurpose());
        verify(patientAppointmentService, times(1)).modifyAppointment(id, modified);
    }

    @Test
    void testDeleteAppointmentPatient() {
        String id = "apt123";

        patientAppointmentController.deleteAppointmentPatient(id);

        verify(patientAppointmentService, times(1)).deleteAppointmentPatient(id);
    }
}
