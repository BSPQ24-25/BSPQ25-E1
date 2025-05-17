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

    /**
     * @brief Constructor of the AdminAppointmentService
     *
     * @param AppointmentRepository
     * @param DoctorRepository
     * @param PatientRepository
     * 
    */
    public AdminAppointmentService(AppointmentRepository appointmentRepository,
                                   DoctorRepository doctorRepository,
                                   PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * @brief Creates the appointment as admin
     * 
     * @param An appointment request received from the controller
     * @return A new appointment or an error (doctor or patient not found)
    */
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

    /**
     * @brief Updates the appointment with new data as admin
     * @param Id of the appointment to be updated
     * @param An appointment with the updated data
     * @return The updated appointment or error (apppointment, doctor or patient not found). Saves the changed appointment
    */
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

    /**
     * @brief Delete an appointment as admin
     * 
     * @param Id of the appointment to be deleted
    */
    public void deleteAppointment(String id) {
        logger.info("Deleting appointment with ID {}", id);
        appointmentRepository.deleteById(id);
    }

    /**
     * @brief Looks for all the doctors as admin
     *
     * @return List of all the doctors
    */
    public List<Doctor> getAllDoctors() {
        logger.info("Fetching all doctors from repository (admin)");
        return doctorRepository.findAll();
    }

    /**
     * @brief Looks for all the patients as admin
     *
     * @return List of all the patients
    */
    public List<Patient> getAllPatients() {
        logger.info("Fetching all patients from repository (admin)");
        return patientRepository.findAll();
    }
    
    /**
     * @brief Looks for all the existing appointments as admin
     *
     * @return List of all the appointments
    */
    public List<Appointment> getAllAppointments() {
        logger.info("Fetching all appointments from repository (admin)");
        return appointmentRepository.findAll();
    }

    /**
     * @brief Generated an id for the appointment usign the current time the appointment is created
     *
     * @return String of the generated appointment ID
    */
    private String generateAppointmentId() {
        String id = "APP" + System.currentTimeMillis();
        logger.debug("Generated new appointment ID: {}", id);
        return id;
    }
}
