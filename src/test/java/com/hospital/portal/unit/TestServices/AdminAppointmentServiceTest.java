package com.hospital.portal.unit.TestServices;

import com.hospital.portal.model.Appointment;
import com.hospital.portal.model.Doctor;
import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.AppointmentRepository;
import com.hospital.portal.repository.DoctorRepository;
import com.hospital.portal.repository.PatientRepository;
import com.hospital.portal.service.AdminAppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdminAppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private AdminAppointmentService adminAppointmentService;

    private Appointment appointmentRequest;
    private Doctor doctor;
    private Patient patient;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setDni("D123");

        patient = new Patient();
        patient.setDni("P123");

        appointmentRequest = new Appointment();
        appointmentRequest.setDoctor(doctor);
        appointmentRequest.setPatient(patient);
        appointmentRequest.setDate(LocalDate.now());
        appointmentRequest.setStartTime(LocalTime.of(9, 0));
        appointmentRequest.setEndTime(LocalTime.of(10, 0));
        appointmentRequest.setAppointmentPurpose("Check-up");
        appointmentRequest.setObservations("Bring reports");
    }

    @Test
    void testCreateAppointment_Success() {
        when(doctorRepository.findByDni("D123")).thenReturn(Optional.of(doctor));
        when(patientRepository.findByDni("P123")).thenReturn(Optional.of(patient));
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(i -> i.getArgument(0));

        Appointment result = adminAppointmentService.createAppointment(appointmentRequest);

        assertNotNull(result);
        assertEquals("Check-up", result.getAppointmentPurpose());
        assertEquals("Bring reports", result.getObservations());
        assertEquals(doctor, result.getDoctor());
        assertEquals(patient, result.getPatient());
    }

    @Test
    void testUpdateAppointment_Success() {
        Appointment existing = new Appointment();
        existing.setAppointmentId("APP001");

        when(appointmentRepository.findById("APP001")).thenReturn(Optional.of(existing));
        when(doctorRepository.findByDni("D123")).thenReturn(Optional.of(doctor));
        when(patientRepository.findByDni("P123")).thenReturn(Optional.of(patient));
        when(appointmentRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Appointment updated = adminAppointmentService.updateAppointment("APP001", appointmentRequest);

        assertNotNull(updated);
        assertEquals("Check-up", updated.getAppointmentPurpose());
        assertEquals(doctor, updated.getDoctor());
        assertEquals(patient, updated.getPatient());
    }

    @Test
    void testUpdateAppointment_NotFound() {
        when(appointmentRepository.findById("APP002")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            adminAppointmentService.updateAppointment("APP002", appointmentRequest));

        assertEquals("Appointment not found", ex.getMessage());
    }

    @Test
    void testDeleteAppointment() {
        adminAppointmentService.deleteAppointment("APP123");
        verify(appointmentRepository, times(1)).deleteById("APP123");
    }

    @Test
    void testGetAllDoctors() {
        List<Doctor> doctors = Arrays.asList(new Doctor(), new Doctor());
        when(doctorRepository.findAll()).thenReturn(doctors);

        List<Doctor> result = adminAppointmentService.getAllDoctors();

        assertEquals(2, result.size());
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPatients() {
        List<Patient> patients = Arrays.asList(new Patient(), new Patient());
        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> result = adminAppointmentService.getAllPatients();

        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void testGetAllAppointments() {
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());
        when(appointmentRepository.findAll()).thenReturn(appointments);

        List<Appointment> result = adminAppointmentService.getAllAppointments();

        assertEquals(2, result.size());
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void testCreateAppointment_DoctorNotFound() {
        when(doctorRepository.findByDni("D123")).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            adminAppointmentService.createAppointment(appointmentRequest));

        assertTrue(ex.getMessage().contains("Doctor not found"));
    }

    @Test
    void testCreateAppointment_PatientNotFound() {
        when(doctorRepository.findByDni("D123")).thenReturn(Optional.of(doctor));
        when(patientRepository.findByDni("P123")).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            adminAppointmentService.createAppointment(appointmentRequest));

        assertTrue(ex.getMessage().contains("Patient not found"));
    }

}