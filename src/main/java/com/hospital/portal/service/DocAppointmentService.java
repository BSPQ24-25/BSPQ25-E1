package com.hospital.portal.service;

import com.hospital.portal.model.Appointment;
import com.hospital.portal.model.Doctor;
import com.hospital.portal.repository.AppointmentRepository;
import com.hospital.portal.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    public DocAppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<Appointment> getAppointmentsByDoctor(String dni) {
        return appointmentRepository.findByDoctorDni(dni);
    }

    public Appointment createAppointment(String dni, Appointment appointmentRequest) {
        Doctor doctor = doctorRepository.findByDni(dni)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with DNI: " + dni));

        Appointment newAppointment = new Appointment();
        newAppointment.setDate(appointmentRequest.getDate());
        newAppointment.setStartTime(appointmentRequest.getStartTime());
        newAppointment.setEndTime(appointmentRequest.getEndTime());
        newAppointment.setDoctor(doctor);
        newAppointment.setPatient(appointmentRequest.getPatient());
        newAppointment.setAppointmentPurpose(appointmentRequest.getAppointmentPurpose());
        newAppointment.setObservations(appointmentRequest.getObservations());

        newAppointment.setAppointmentId(generateAppointmentId());

        return appointmentRepository.save(newAppointment);
    }

    private String generateAppointmentId() {
        return "APP" + System.currentTimeMillis();
    }

    public Appointment updateAppointment(String id, Appointment newAppointmentData) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (newAppointmentData.getDate() != null) {
            existing.setDate(newAppointmentData.getDate());
        }

        if (newAppointmentData.getStartTime() != null) {
            existing.setStartTime(newAppointmentData.getStartTime());
        }

        if (newAppointmentData.getEndTime() != null) {
            existing.setEndTime(newAppointmentData.getEndTime());
        }

        if (newAppointmentData.getPatient() != null) {
            existing.setPatient(newAppointmentData.getPatient());
        }

        if (newAppointmentData.getAppointmentPurpose() != null) {
            existing.setAppointmentPurpose(newAppointmentData.getAppointmentPurpose());
        }

        if (newAppointmentData.getObservations() != null) {
            existing.setObservations(newAppointmentData.getObservations());
        }

        return appointmentRepository.save(existing);
    }

    public void deleteAppointment(String id) {
        appointmentRepository.deleteById(id);
    }
}
