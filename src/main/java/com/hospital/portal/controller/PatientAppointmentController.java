package com.hospital.portal.controller;
import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.portal.model.*;

import com.hospital.portal.service.PatientAppointmentService;


@RestController
@RequestMapping("/api/patients")
public class PatientAppointmentController {


    private final PatientAppointmentService patientAppointmentService;
    private static final Logger logger = LogManager.getLogger(PatientAppointmentController.class);

    /** 
     * @brief Constructor of the PatientsAppointments
    */
    public PatientAppointmentController(PatientAppointmentService patientAppointmentService){
        this.patientAppointmentService = patientAppointmentService;
    }

    /** 
     * @brief Gets all the patients
     * @return List of all the patients
    */
    @GetMapping("/")
    public List<Patient> getAllPatients() {
        logger.info("Entering into Controller getAllPatients");
        return patientAppointmentService.getAllPatients();
    }

    /** 
     * @brief Gets all the appointments of a specific patient
     * @param DNI of the patient
     * @return List of all the appointments of the specified patient
    */
    @GetMapping("/{patientDNI}/appointments")
    public List<Appointment> getAppointmentsByPatientDNI(@PathVariable String patientDNI) {
        logger.info("Entering into Controller getAppointmentsByPatientDNI with DNI {}",patientDNI);
        return patientAppointmentService.getAppointmentsByPatientDNI(patientDNI);
    }

    /** 
     * @brief Filters the information of a patients appointment by date
     * @param DNI of the patient
     * @param From what date is wanted to retrieve appointments
     * @return List of appointments starting form the date specified
    */
    @GetMapping("/{patientDNI}/appointmentsByDate")
    public List<Appointment> getAppointmentsByDate(@PathVariable String patientDNI, @RequestParam LocalDate date) {
        logger.info("Entering into Controller getAppointmentsByDate with DNI {}",patientDNI);

        return patientAppointmentService.getAppointmentsByDate(patientDNI, date);
    }

    /** 
     * @brief Filters the information of a patients appointment by speciality od the doctor
     * @param DNI of the patient
     * @param Speaciality of the doctor
     * @return List of appointments that have doctors of said speciality
    */
    @GetMapping("/{patientDNI}/appointmentsBySpecialty")
    public List<Appointment> getAppointmentByPatientDniAndDoctorSpecialtyName(@PathVariable String patientDNI, @RequestParam String specialty_name) {
        logger.info("Entering into Controller getAppointmentByPatientDniAndDoctorSpecialtyName with DNI",patientDNI);

        return patientAppointmentService.getAppointmentByPatientDniAndDoctorSpecialtyName(patientDNI, specialty_name);
    }

    /** 
     * @brief Creates an appointment for a specific patient
     * @param DNI of the patient
     * @param New appointment created
     * @return The new appointment created
    */
    @PostMapping("/{patientDNI}/appointments")
    public Appointment addAppointmentPatient(@PathVariable String patientDNI, @RequestBody Appointment appointment) {
        logger.info("Entering into Controller addAppointmentPatient with DNI {}",patientDNI);

        return patientAppointmentService.addAppointmentPatient(patientDNI, appointment);
    }


    /** 
     * @brief Updates a specific appointment
     * @param Id of the appointment to be edited
     * @param Appointment updated
     * @return The appointment updated
    */
    @PutMapping("/appointments/{appointmentId}")
    public Appointment modifyAppointment(@PathVariable String appointmentId, @RequestBody Appointment modifiedAppointment){
        logger.info("Entering into Controller modifyAppointment with appointment ID {}",appointmentId);

        return patientAppointmentService.modifyAppointment(appointmentId, modifiedAppointment);
    }

    /** 
     * @brief Deletes a specific appointment 
     * @param Id of the appointment to be deleted
    */
    @DeleteMapping("/appointments/{appointmentId}")
    public void deleteAppointmentPatient(@PathVariable String appointmentId) {
        logger.info("Entering into Controller deleteAppointmentPatient  with appointment ID {}",appointmentId);

        patientAppointmentService.deleteAppointmentPatient(appointmentId);
    }
    
}
