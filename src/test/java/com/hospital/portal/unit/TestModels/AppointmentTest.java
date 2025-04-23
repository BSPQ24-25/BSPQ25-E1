package com.hospital.portal.unit.TestModels;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.hospital.portal.model.Appointment;
import com.hospital.portal.model.Doctor;
import com.hospital.portal.model.Patient;

class AppointmentTest {

    private Appointment appointment;
    private Patient mockPatient;
    private Doctor mockDoctor;

    @BeforeEach
    void setUp() {
        mockPatient = mock(Patient.class);
        when(mockPatient.getPatientId()).thenReturn("patient123");

        mockDoctor = mock(Doctor.class);
        when(mockDoctor.getDoctorId()).thenReturn("doctor456");

        appointment = new Appointment(
                LocalDate.of(2025, 5, 10),
                LocalTime.of(10, 0),
                LocalTime.of(10, 30),
                mockPatient,
                mockDoctor,
                "General Checkup"
        );
    }

    @Test
    @DisplayName("Should correctly set and get appointmentId")
    void testAppointmentId() {
        appointment.setAppointmentId("appt001");
        assertEquals("appt001", appointment.getAppointmentId());
    }

    @Test
    @DisplayName("Should correctly set and get date")
    void testDate() {
        appointment.setDate(LocalDate.of(2025, 5, 12));
        assertEquals(LocalDate.of(2025, 5, 12), appointment.getDate());
    }

    @Test
    @DisplayName("Should correctly set and get start time")
    void testStartTime() {
        appointment.setStartTime(LocalTime.of(11, 0));
        assertEquals(LocalTime.of(11, 0), appointment.getStartTime());
    }

    @Test
    @DisplayName("Should correctly set and get end time")
    void testEndTime() {
        appointment.setEndTime(LocalTime.of(11, 30));
        assertEquals(LocalTime.of(11, 30), appointment.getEndTime());
    }

    @Test
    @DisplayName("Should correctly set and get patient")
    void testPatient() {
        appointment.setPatient(mockPatient);
        assertNotNull(appointment.getPatient());
        assertEquals("patient123", appointment.getPatient().getPatientId());
    }

    @Test
    @DisplayName("Should correctly set and get doctor")
    void testDoctor() {
        appointment.setDoctor(mockDoctor);
        assertNotNull(appointment.getDoctor());
        assertEquals("doctor456", appointment.getDoctor().getDoctorId());
    }

    @Test
    @DisplayName("Should correctly set and get appointmentPurpose")
    void testAppointmentPurpose() {
        appointment.setAppointmentPurpose("Routine Checkup");
        assertEquals("Routine Checkup", appointment.getAppointmentPurpose());
    }

    @Test
    @DisplayName("Should correctly set and get observations")
    void testObservations() {
        appointment.setObservations("Patient is in good health.");
        assertEquals("Patient is in good health.", appointment.getObservations());
    }

    @Test
    @DisplayName("Should return correct string representation")
    void testToString() {
        appointment.setAppointmentId("appt001");
        appointment.setDate(LocalDate.of(2025, 5, 10));
        appointment.setStartTime(LocalTime.of(10, 0));
        appointment.setEndTime(LocalTime.of(10, 30));
        appointment.setPatient(mockPatient);
        appointment.setDoctor(mockDoctor);
        appointment.setAppointmentPurpose("General Checkup");
        appointment.setObservations("All good");

        String result = appointment.toString();
        assertTrue(result.contains("appointmentId='appt001'"));
        assertTrue(result.contains("date=2025-05-10"));
        assertTrue(result.contains("startTime=10:00"));
        assertTrue(result.contains("endTime=10:30"));
        assertTrue(result.contains("patient=patient123"));
        assertTrue(result.contains("doctor=doctor456"));
        assertTrue(result.contains("appointmentPurpose='General Checkup'"));
        assertTrue(result.contains("observations='All good'"));
    }
}
