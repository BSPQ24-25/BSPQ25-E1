package com.hospital.portal.unit.TestControllers;

import com.hospital.portal.controller.AdminAppointmentController;
import com.hospital.portal.model.Appointment;
import com.hospital.portal.model.Doctor;
import com.hospital.portal.model.Patient;
import com.hospital.portal.service.AdminAppointmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminAppointmentControllerTest {

    @Mock
    private AdminAppointmentService adminAppointmentService;

    @InjectMocks
    private AdminAppointmentController adminAppointmentController;

    private Appointment appointment;

    @BeforeEach
    void setUp() {
        appointment = new Appointment(
            LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), null, null, "General Check"
        );
    }

    @Test
    void testCreateAppointment() {
        when(adminAppointmentService.createAppointment(appointment)).thenReturn(appointment);

        Appointment result = adminAppointmentController.createAppointment(appointment);

        assertNotNull(result);
        assertEquals("General Check", result.getAppointmentPurpose());
        verify(adminAppointmentService, times(1)).createAppointment(appointment);
    }

    @Test
    void testUpdateAppointment() {
        String appointmentId = "apt001";
        Appointment updated = new Appointment(
            LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(12, 0), null, null, "Updated Check"
        );

        when(adminAppointmentService.updateAppointment(appointmentId, updated)).thenReturn(updated);

        Appointment result = adminAppointmentController.updateAppointment(appointmentId, updated);

        assertNotNull(result);
        assertEquals("Updated Check", result.getAppointmentPurpose());
        verify(adminAppointmentService, times(1)).updateAppointment(appointmentId, updated);
    }

    @Test
    void testDeleteAppointment() {
        String appointmentId = "apt002";

        adminAppointmentController.deleteAppointment(appointmentId);

        verify(adminAppointmentService, times(1)).deleteAppointment(appointmentId);
    }

    @Test
    void testGetAllDoctors() {
        Doctor doc = new Doctor();
        List<Doctor> doctors = Arrays.asList(doc);

        when(adminAppointmentService.getAllDoctors()).thenReturn(doctors);

        List<Doctor> result = adminAppointmentController.getAllDoctors();

        assertEquals(1, result.size());
        verify(adminAppointmentService, times(1)).getAllDoctors();
    }

    @Test
    void testGetAllPatients() {
        Patient patient = new Patient();
        List<Patient> patients = Arrays.asList(patient);

        when(adminAppointmentService.getAllPatients()).thenReturn(patients);

        List<Patient> result = adminAppointmentController.getAllPatients();

        assertEquals(1, result.size());
        verify(adminAppointmentService, times(1)).getAllPatients();
    }

    @Test
    void testGetAllAppointments() {
        List<Appointment> appointments = Arrays.asList(appointment);

        when(adminAppointmentService.getAllAppointments()).thenReturn(appointments);

        List<Appointment> result = adminAppointmentController.getAllAppointments();

        assertEquals(1, result.size());
        assertEquals("General Check", result.get(0).getAppointmentPurpose());
        verify(adminAppointmentService, times(1)).getAllAppointments();
    }
}


// mvn jacoco:report
