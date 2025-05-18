package com.hospital.portal.service;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hospital.portal.model.Appointment;
import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.AppointmentRepository;
import com.hospital.portal.repository.PatientRepository;


@Service
public class PatientAppointmentService {
    private static final Logger logger = LogManager.getLogger(PatientAppointmentService.class);

    
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;  

    /**
     * @brief Constructor of the PatientAppointmentService
     *
     * @param AppointmentRepository
     * @param PatientRepository
     * 
    */
    public PatientAppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository){
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository; 
    }

    /**
     * @brief Looks for all the patients as a Patient
     *
     * @return List of all the patients
    */
    public List<Patient> getAllPatients() {
      logger.info("Feching all patients from repository");

      return patientRepository.findAll();
    }

    /**
     * @brief Looks for all the appointments the patient logged in has
     * @param DNI of the patient 
     * @return List of all the appointments 
    */
    public List<Appointment> getAppointmentsByPatientDNI(String patientDNI) {
        logger.info("Obtaining Appointments forDNI: {}", patientDNI);

        return appointmentRepository.findByPatientDni(patientDNI);
    }

    /**
     * @brief Looks for all the appointments the patient logged in has and filters them by a specified doctor speciality
     * @param DNI of the patient
     * @param Name of the speciality  
     * @return List of all the appointments that match the requirements
    */
    public List<Appointment> getAppointmentByPatientDniAndDoctorSpecialtyName(String patientDni, String specialty_name) {
        logger.info("Obtaining Appointments for specialty and DNI: {}", patientDni);
        return appointmentRepository.findByPatientDniAndDoctorSpecialtyName(patientDni, specialty_name);
    }

    /** 
     * @brief Filters the information of a patients appointment by date
     * @param DNI of the patient
     * @param From what date is wanted to retrieve appointments
     * @return List of appointments starting form the date specified
    */
    public List<Appointment> getAppointmentsByDate(String patientDNI, LocalDate dateTime) {
        logger.info("Obtaining Appointments by date and DNI: {}", patientDNI);
        return appointmentRepository.findByPatientDniAndDate(patientDNI, dateTime);
    }
    
    /** 
     * @brief Creates an appointment for a specific patient
     * @param DNI of the patient
     * @param New appointment created
     * @return The new appointment created
    */
    public Appointment addAppointmentPatient(String pateintDNI, Appointment appointment) {
        logger.info("Adding Appointments to DNI: {}", pateintDNI);

        Patient patient = patientRepository.findByDni(pateintDNI).orElseThrow(() -> new IllegalArgumentException("Patient not found with DNI: " + pateintDNI));

        Appointment newAppointment = new Appointment();
        newAppointment.setDate(appointment.getDate());
        newAppointment.setStartTime(appointment.getStartTime());
        newAppointment.setEndTime(appointment.getEndTime());
        newAppointment.setDoctor(appointment.getDoctor());
        newAppointment.setPatient(patient);
        newAppointment.setAppointmentPurpose(appointment.getAppointmentPurpose());
        newAppointment.setObservations(appointment.getObservations());
        newAppointment.setAppointmentId(generateAppointmentId());

        return appointmentRepository.save(newAppointment);

    }

    /**
     * @brief Generated an id for the appointment using the current time the appointment is created
     *
     * @return String of the generated appointment ID
    */
    private String generateAppointmentId() {
        return "APP" + System.currentTimeMillis(); 
    }

    /**
     * @brief Updates the appointment with new data as patient
     * @param Id of the appointment to be updated
     * @param An appointment with the updated data
     * @return The updated appointment. Saves the changed appointment
    */
    public Appointment modifyAppointment(String id, Appointment modifiedAppointment){
        logger.info("Modifying Appointments to id: {}", id);

        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + id));
        
        if (modifiedAppointment.getDate() != null) {
            appointment.setDate(modifiedAppointment.getDate());
        }
        
        if (modifiedAppointment.getStartTime() != null) {
            appointment.setStartTime(modifiedAppointment.getStartTime());
        }
        
        if (modifiedAppointment.getEndTime() != null) {
            appointment.setEndTime(modifiedAppointment.getEndTime());
        }
        
        if (modifiedAppointment.getDoctor() != null) {
            appointment.setDoctor(modifiedAppointment.getDoctor());
        }
        
        if (modifiedAppointment.getAppointmentPurpose() != null) {
            appointment.setAppointmentPurpose(modifiedAppointment.getAppointmentPurpose());
        }

        if (modifiedAppointment.getObservations() != null) {
            appointment.setObservations(modifiedAppointment.getObservations());
        }

        return appointmentRepository.save(appointment);

    }
    
    /**
     * @brief Delete an appointment as doctor
     * 
     * @param Id of the appointment to be deleted
    */
    public void deleteAppointmentPatient(String id) {
        logger.info("Deleting Appointments to id: {}", id);

        appointmentRepository.deleteById(id);
    }

    
}

