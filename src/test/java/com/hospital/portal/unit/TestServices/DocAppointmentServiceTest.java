package com.hospital.portal.unit.TestServices;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hospital.portal.model.Appointment;
import com.hospital.portal.model.Doctor;
import com.hospital.portal.repository.AppointmentRepository;
import com.hospital.portal.repository.DoctorRepository;
import com.hospital.portal.service.DocAppointmentService;

@ExtendWith(MockitoExtension.class)
class DocAppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DocAppointmentService docAppointmentService;

    private Appointment appointment;
    private Doctor doctor;

    @BeforeEach
    void setUp() {
        // Setup mock data
        doctor = new Doctor();
        doctor.setDoctorId("doc123");
        doctor.setName("Dr. Smith");

        appointment = new Appointment(
            LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), null, doctor, "Routine Check"
        );
    }

    @Test
    void testGetAppointmentsByDoctor() {
        String doctorId = "doc123";
        // Mock the behavior of the repository
        when(appointmentRepository.findByDoctorDoctorId(doctorId)).thenReturn(List.of(appointment));

        // Call the service method
        var result = docAppointmentService.getAppointmentsByDoctor(doctorId);

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Routine Check", result.get(0).getAppointmentPurpose());
        verify(appointmentRepository, times(1)).findByDoctorDoctorId(doctorId);
    }

    @Test
    void testCreateAppointment() {
        String doctorId = "doc123";
        Appointment newAppointment = new Appointment(
            LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(12, 0), null, doctor, "Follow-up"
        );

        // Mock the behavior of doctorRepository
        when(doctorRepository.findByDoctorId(doctorId)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(newAppointment);

        // Call the service method
        Appointment result = docAppointmentService.createAppointment(doctorId, newAppointment);

        // Verify the result
        assertNotNull(result);
        assertEquals("Follow-up", result.getAppointmentPurpose());
        verify(doctorRepository, times(1)).findByDoctorId(doctorId);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testUpdateAppointment() {
        String appointmentId = "apt123";
        Appointment updatedAppointment = new Appointment(
            LocalDate.now(), LocalTime.of(10, 30), LocalTime.of(11, 30), null, doctor, "Updated Check-up"
        );

        Appointment existingAppointment = new Appointment(
            LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), null, doctor, "Routine Check"
        );

        // Mock the repository behavior
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(existingAppointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(updatedAppointment);

        // Call the service method
        Appointment result = docAppointmentService.updateAppointment(appointmentId, updatedAppointment);

        // Verify the result
        assertNotNull(result);
        assertEquals("Updated Check-up", result.getAppointmentPurpose());
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testDeleteAppointment() {
        String appointmentId = "apt123";

        // Call the service method
        docAppointmentService.deleteAppointment(appointmentId);

        // Verify the repository method was called
        verify(appointmentRepository, times(1)).deleteById(appointmentId);
    }

    @Test
    void testCreateAppointmentWhenDoctorNotFound() {
        String doctorId = "doc123";
        Appointment newAppointment = new Appointment(
            LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(12, 0), null, doctor, "Follow-up"
        );

        // Mock the behavior of doctorRepository to return empty
        when(doctorRepository.findByDoctorId(doctorId)).thenReturn(Optional.empty());

        // Test that creating an appointment throws an exception when the doctor is not found
        assertThrows(IllegalArgumentException.class, () -> {
            docAppointmentService.createAppointment(doctorId, newAppointment);
        });

        verify(doctorRepository, times(1)).findByDoctorId(doctorId);
        verify(appointmentRepository, never()).save(any(Appointment.class));  // Ensure save is never called
    }
}
