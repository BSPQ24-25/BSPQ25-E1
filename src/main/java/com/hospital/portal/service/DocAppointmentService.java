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
import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.AppointmentRepository;
import com.hospital.portal.repository.DoctorRepository;
import com.hospital.portal.repository.PatientRepository;

@Service
public class DocAppointmentService {
    private static final Logger logger = LogManager.getLogger(DocAppointmentService.class);


    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    /**
     * @brief Constructor of the DocAppointmentService
     *
     * @param AppointmentRepository
     * @param DoctorRepository
     * @param PatientRepository
     * 
    */
    public DocAppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }
    
    /**
     * @brief Looks for all the doctors as a Doctor
     *
     * @return List of all the doctors
    */
    public List<Doctor> getAllDoctors() {
        logger.info("Fetching all doctors from repository");
        return doctorRepository.findAll();
    }
    
    /**
     * @brief Looks for all the patients as a Doctor
     *
     * @return List of all the patients
    */
    public List<Patient> getAllPatients() {
        logger.info("Fetching all patients from repository");
        return patientRepository.findAll();
    }

    /**
     * @brief Looks for all the appointments the doctor logged in has
     * @param DNI of the doctor 
     * @return List of all the appointments 
    */
    public List<Appointment> getAppointmentsByDoctor(String dni) {
        logger.info("Returning Doctor by DNI {}",dni);

        return appointmentRepository.findByDoctorDni(dni);
    }

    /**
     * @brief Creates the appointment as doctor
     * 
     * @param DNI of the doctor who is creating the new citation
     * @param An appointment request received from the controller
     * @return A new appointment or an error (doctor not found)
    */
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

    /**
     * @brief Generated an id for the appointment usingthe current time the appointment is created
     *
     * @return String of the generated appointment ID
    */
    private String generateAppointmentId() {
        return "APP" + System.currentTimeMillis();
    }

    /**
     * @brief Updates the appointment with new data as doctor
     * @param Id of the appointment to be updated
     * @param An appointment with the updated data
     * @return The updated appointment or error (apppointment not found). Saves the changed appointment
    */
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

    /**
     * @brief Delete an appointment as doctor
     * 
     * @param Id of the appointment to be deleted
    */
    public void deleteAppointment(String id) {
        logger.info("Deleting appointment for id {}", id);

        appointmentRepository.deleteById(id);
    }
    
    /**
     * @brief List all the appointments the specified doctor has in a range of dates
     * 
     * @param DNI of the doctor
     * @param From what date is wanted to retrieve appointments
     * @param To what date is wanted to retrieve appointments
     * @return List of appointments between the dates specified
    */
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
