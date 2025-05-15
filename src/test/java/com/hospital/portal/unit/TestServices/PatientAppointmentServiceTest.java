package com.hospital.portal.unit.TestServices;

import com.hospital.portal.model.Appointment;
import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.AppointmentRepository;
import com.hospital.portal.repository.PatientRepository;
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
import java.util.*;

@ExtendWith(MockitoExtension.class)
class PatientAppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientAppointmentService patientAppointmentService;

    private Appointment appointment;
    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setDni("pat001");

        appointment = new Appointment();
        appointment.setAppointmentId("APP123");
        appointment.setDate(LocalDate.now());
        appointment.setStartTime(LocalTime.of(9, 0));
        appointment.setEndTime(LocalTime.of(10, 0));
        appointment.setPatient(patient);
        appointment.setAppointmentPurpose("Consulta");
    }

    @Test
    void testGetAppointmentsByPatientDNI() {
        when(appointmentRepository.findByPatientDni("pat001")).thenReturn(List.of(appointment));

        List<Appointment> result = patientAppointmentService.getAppointmentsByPatientDNI("pat001");

        assertEquals(1, result.size());
        verify(appointmentRepository).findByPatientDni("pat001");
    }

    @Test
    void testGetAppointmentByPatientDniAndDoctorSpecialtyName() {
        when(appointmentRepository.findByPatientDniAndDoctorSpecialtyName("pat001", "Cardiology"))
            .thenReturn(List.of(appointment));

        List<Appointment> result = patientAppointmentService.getAppointmentByPatientDniAndDoctorSpecialtyName("pat001", "Cardiology");

        assertEquals(1, result.size());
        verify(appointmentRepository).findByPatientDniAndDoctorSpecialtyName("pat001", "Cardiology");
    }

    @Test
    void testGetAppointmentsByDate() {
        LocalDate date = LocalDate.now();
        when(appointmentRepository.findByPatientDniAndDate("pat001", date)).thenReturn(List.of(appointment));

        List<Appointment> result = patientAppointmentService.getAppointmentsByDate("pat001", date);

        assertEquals(1, result.size());
        verify(appointmentRepository).findByPatientDniAndDate("pat001", date);
    }

    @Test
    void testAddAppointmentPatient() {
        when(patientRepository.findByDni("pat001")).thenReturn(Optional.of(patient));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment result = patientAppointmentService.addAppointmentPatient("pat001", appointment);

        assertNotNull(result);
        assertEquals("Consulta", result.getAppointmentPurpose());
        verify(patientRepository).findByDni("pat001");
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    void testAddAppointmentPatient_ThrowsException_WhenPatientNotFound() {
        when(patientRepository.findByDni("invalidDni")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            patientAppointmentService.addAppointmentPatient("invalidDni", appointment);
        });

        assertTrue(exception.getMessage().contains("Patient not found with DNI"));
        verify(patientRepository).findByDni("invalidDni");
    }

    @Test
    void testModifyAppointment() {
        when(appointmentRepository.findById("APP123")).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment modified = new Appointment();
        modified.setAppointmentPurpose("Revisión");
        modified.setObservations("Nuevas observaciones");

        Appointment result = patientAppointmentService.modifyAppointment("APP123", modified);

        assertEquals("Revisión", result.getAppointmentPurpose());
        assertEquals("Nuevas observaciones", result.getObservations());
        verify(appointmentRepository).findById("APP123");
        verify(appointmentRepository).save(appointment);
    }

    @Test
    void testModifyAppointment_ThrowsException_WhenNotFound() {
        when(appointmentRepository.findById("INVALID")).thenReturn(Optional.empty());

        Appointment modified = new Appointment();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            patientAppointmentService.modifyAppointment("INVALID", modified);
        });

        assertTrue(exception.getMessage().contains("Appointment not found with ID"));
        verify(appointmentRepository).findById("INVALID");
    }

    @Test
    void testDeleteAppointmentPatient() {
        doNothing().when(appointmentRepository).deleteById("APP123");

        patientAppointmentService.deleteAppointmentPatient("APP123");

        verify(appointmentRepository).deleteById("APP123");
    }
}
