package com.hospital.portal.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hospital.portal.model.Appointment;
import com.hospital.portal.model.Doctor;
import com.hospital.portal.repository.AppointmentRepository;
import com.hospital.portal.repository.DoctorRepository;

@Service
public class DocAppointmentService {
    private static final Logger logger = LogManager.getLogger(DocAppointmentService.class);


    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    public DocAppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<Appointment> getAppointmentsByDoctor(String dni) {
        logger.info("Returning Doctor by DNI {}",dni);

        return appointmentRepository.findByDoctorDni(dni);
    }

    public Appointment createAppointment(String dni, Appointment appointmentRequest) {
        logger.info("Creating appointment for DNI {}",dni);

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
        logger.info("Updating appointment for id {}", id);

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
        logger.info("Deleting appointment for id {}", id);

        appointmentRepository.deleteById(id);
    }
    
    public List<Appointment> getAppointmentsByDoctorAndDateRange(String dni, LocalDate startDate, LocalDate endDate) {
        List<Appointment> allAppointments = appointmentRepository.findByDoctorDni(dni);

        logger.info("Getting Appointments by Doctor and range for DNI {}", dni);

        return allAppointments.stream()
                .filter(app -> {
                    LocalDate date = app.getDate();
                    return !date.isBefore(startDate) && !date.isAfter(endDate);
                })
                .sorted(Comparator.comparing(Appointment::getDate)
                                  .thenComparing(Appointment::getStartTime))
                .collect(Collectors.toList());
    }
}
