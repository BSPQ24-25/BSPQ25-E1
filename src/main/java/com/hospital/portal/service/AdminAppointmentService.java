package com.hospital.portal.service;

import com.hospital.portal.model.Appointment;
import com.hospital.portal.model.Doctor;
import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.AppointmentRepository;
import com.hospital.portal.repository.DoctorRepository;
import com.hospital.portal.repository.PatientRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminAppointmentService {

    private static final Logger logger = LogManager.getLogger(AdminAppointmentService.class);

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AdminAppointmentService(AppointmentRepository appointmentRepository,
                                   DoctorRepository doctorRepository,
                                   PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public Appointment createAppointment(Appointment appointmentRequest) {
        logger.info("Creating appointment as admin");

        Doctor doctor = doctorRepository.findByDni(appointmentRequest.getDoctor().getDni())
                .orElseThrow(() -> {
                    logger.error("Doctor not found with DNI: {}", appointmentRequest.getDoctor().getDni());
                    return new IllegalArgumentException("Doctor not found with DNI: " + appointmentRequest.getDoctor().getDni());
                });

        Patient patient = patientRepository.findByDni(appointmentRequest.getPatient().getDni())
                .orElseThrow(() -> {
                    logger.error("Patient not found with DNI: {}", appointmentRequest.getPatient().getDni());
                    return new IllegalArgumentException("Patient not found with DNI: " + appointmentRequest.getPatient().getDni());
                });

        Appointment newAppointment = new Appointment();
        newAppointment.setDoctor(doctor);
        newAppointment.setPatient(patient);
        newAppointment.setDate(appointmentRequest.getDate());
        newAppointment.setStartTime(appointmentRequest.getStartTime());
        newAppointment.setEndTime(appointmentRequest.getEndTime());
        newAppointment.setAppointmentPurpose(appointmentRequest.getAppointmentPurpose());
        newAppointment.setObservations(appointmentRequest.getObservations());
        newAppointment.setAppointmentId(generateAppointmentId());

        logger.info("Saving new appointment with ID {}", newAppointment.getAppointmentId());
        return appointmentRepository.save(newAppointment);
    }


    public Appointment updateAppointment(String id, Appointment updatedData) {
        logger.info("Updating appointment with ID {}", id);

        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Appointment not found with ID {}", id);
                    return new RuntimeException("Appointment not found");
                });

        if (updatedData.getDate() != null) existing.setDate(updatedData.getDate());
        if (updatedData.getStartTime() != null) existing.setStartTime(updatedData.getStartTime());
        if (updatedData.getEndTime() != null) existing.setEndTime(updatedData.getEndTime());
        if (updatedData.getAppointmentPurpose() != null) existing.setAppointmentPurpose(updatedData.getAppointmentPurpose());
        if (updatedData.getObservations() != null) existing.setObservations(updatedData.getObservations());

        if (updatedData.getDoctor() != null) {
            Doctor doctor = doctorRepository.findByDni(updatedData.getDoctor().getDni())
                    .orElseThrow(() -> {
                        logger.error("Doctor not found with DNI: {}", updatedData.getDoctor().getDni());
                        return new IllegalArgumentException("Doctor not found with DNI: " + updatedData.getDoctor().getDni());
                    });
            existing.setDoctor(doctor);
        }

        if (updatedData.getPatient() != null) {
            Patient patient = patientRepository.findByDni(updatedData.getPatient().getDni())
                    .orElseThrow(() -> {
                        logger.error("Patient not found with DNI: {}", updatedData.getPatient().getDni());
                        return new IllegalArgumentException("Patient not found with DNI: " + updatedData.getPatient().getDni());
                    });
            existing.setPatient(patient);
        }

        logger.info("Saving updated appointment with ID {}", existing.getAppointmentId());
        return appointmentRepository.save(existing);
    }

    public void deleteAppointment(String id) {
        logger.info("Deleting appointment with ID {}", id);
        appointmentRepository.deleteById(id);
    }

    public List<Doctor> getAllDoctors() {
        logger.info("Fetching all doctors from repository (admin)");
        return doctorRepository.findAll();
    }

    public List<Patient> getAllPatients() {
        logger.info("Fetching all patients from repository (admin)");
        return patientRepository.findAll();
    }
    
    public List<Appointment> getAllAppointments() {
        logger.info("Fetching all appointments from repository (admin)");
        return appointmentRepository.findAll();
    }

    private String generateAppointmentId() {
        String id = "APP" + System.currentTimeMillis();
        logger.debug("Generated new appointment ID: {}", id);
        return id;
    }
}
