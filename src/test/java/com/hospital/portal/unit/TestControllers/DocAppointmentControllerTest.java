package com.hospital.portal.unit.TestControllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hospital.portal.controller.DocAppointmentController;
import com.hospital.portal.model.Appointment;
import com.hospital.portal.service.DocAppointmentService;

@ExtendWith(MockitoExtension.class)
class DocAppointmentControllerTest {

    @Mock
    private DocAppointmentService docAppointmentService;

    @InjectMocks
    private DocAppointmentController docAppointmentController;

    private Appointment appointment;

    @BeforeEach
    void setUp() {
        appointment = new Appointment(
            LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), null, null, "Routine Check"
        );
    }

    @Test
    void testGetAppointmentsByDoctor() {
        String doctorDNI = "doc123";
        List<Appointment> appointments = Arrays.asList(appointment);

        // Mock the service method
        when(docAppointmentService.getAppointmentsByDoctor(doctorDNI)).thenReturn(appointments);

        // Call the controller method
        List<Appointment> result = docAppointmentController.getAppointmentsByDoctor(doctorDNI);

        // Verify the result
        assertEquals(1, result.size());
        assertEquals("Routine Check", result.get(0).getAppointmentPurpose());
        verify(docAppointmentService, times(1)).getAppointmentsByDoctor(doctorDNI);
    }

    @Test
    void testCreateAppointment() {
        String doctorDNI = "doc123";
        Appointment newAppointment = new Appointment(
            LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(12, 0), null, null, "Follow-up"
        );

        // Mock the service method
        when(docAppointmentService.createAppointment(doctorDNI, newAppointment)).thenReturn(newAppointment);

        // Call the controller method
        Appointment result = docAppointmentController.createAppointment(doctorDNI, newAppointment);

        // Verify the result
        assertNotNull(result);
        assertEquals("Follow-up", result.getAppointmentPurpose());
        verify(docAppointmentService, times(1)).createAppointment(doctorDNI, newAppointment);
    }

    @Test
    void testUpdateAppointment() {
        String appointmentId = "apt123";
        Appointment updatedAppointment = new Appointment(
            LocalDate.now(), LocalTime.of(10, 30), LocalTime.of(11, 30), null, null, "Updated Check-up"
        );

        // Mock the service method
        when(docAppointmentService.updateAppointment(appointmentId, updatedAppointment)).thenReturn(updatedAppointment);

        // Call the controller method
        Appointment result = docAppointmentController.updateAppointment(appointmentId, updatedAppointment);

        // Verify the result
        assertNotNull(result);
        assertEquals("Updated Check-up", result.getAppointmentPurpose());
        verify(docAppointmentService, times(1)).updateAppointment(appointmentId, updatedAppointment);
    }

    @Test
    void testDeleteAppointment() {
        String appointmentId = "apt123";

        // Call the controller method
        docAppointmentController.deleteAppointment(appointmentId);

        // Verify the service method was called
        verify(docAppointmentService, times(1)).deleteAppointment(appointmentId);
    }
}
