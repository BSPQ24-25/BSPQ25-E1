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

import com.hospital.portal.model.Appointment;
import com.hospital.portal.model.Doctor;
import com.hospital.portal.model.Patient;
import com.hospital.portal.service.DocAppointmentService;


@RestController
@RequestMapping("/api/doctors")
public class DocAppointmentController {

    private static final Logger logger = LogManager.getLogger(DocAppointmentController.class);


    private final DocAppointmentService docAppointmentService;

    /** 
     * @brief Constructor of the appointments for the doctors
     * 
    */
    public DocAppointmentController(DocAppointmentService docAppointmentService) {
        this.docAppointmentService = docAppointmentService;
    }
    
    /** 
     * @brief  Gets the doctors
     * 
     * @return List of all the doctors
    */
    @GetMapping("/doctors")
    public List<Doctor> getAllDoctors() {
        logger.info("Entering into Controller getAllDoctors");
        return docAppointmentService.getAllDoctors();
    }
    
    /** 
     * @brief  Gets the patients
     * 
     * @return List of all the patients
    */
    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        logger.info("Entering into Controller getAllPatients");
        return docAppointmentService.getAllPatients();
    }

    /** 
     * @brief  Gets the appointments of a specific doctor
     * @param DNI of the doctor
     * @return List of all the appointments of the doctor specified
    */
    @GetMapping("/{dni}/appointments")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable String dni) {
        logger.info("Entering into Controller getAppointmentsByDoctor with DNI {}",dni);

        return docAppointmentService.getAppointmentsByDoctor(dni);
    }

    /** 
     * @brief Creates an appointment for a specific doctor
     * @param DNI of the doctor
     * @param New appointment created
     * @return The new appointment created
    */
    @PostMapping("/{dni}/appointments")
    public Appointment createAppointment(@PathVariable String dni, @RequestBody Appointment appointment) {
        logger.info("Entering into Controller createAppointment with DNI {}",dni);

        return docAppointmentService.createAppointment(dni, appointment);
    }

    /** 
     * @brief Updates a specific appointment
     * @param Id of the appointment to be edited
     * @param Appointment updated
     * @return The appointment updated
    */
    @PutMapping("/appointments/{appointmentId}")
    public Appointment updateAppointment(@PathVariable String appointmentId, @RequestBody Appointment appointment) {
        logger.info("Entering into Controller updateAppointment with appointment id {}",appointmentId);

        return docAppointmentService.updateAppointment(appointmentId, appointment);
    }

    /** 
     * @brief Deletes a specific appointment 
     * @param Id of the appointment to be deleted
    */
    @DeleteMapping("/appointments/{appointmentId}")
    public void deleteAppointment(@PathVariable String appointmentId) {
        logger.info("Entering into Controller deleteAppointment with appointment id {}",appointmentId);
        docAppointmentService.deleteAppointment(appointmentId);
    }
    
    /** 
     * @brief Filters the information of a doctors appointment by date
     * @param DNI of the doctor
     * @param From what date is wanted to retrieve appointments
     * @param To what date is wanted to retrieve appointments
     * @return List of appointments between the dates specified
    */
    @GetMapping("/{dni}/appointments/filter")
    public List<Appointment> getAppointmentsByDoctorAndDateRange(@PathVariable String dni, @RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        logger.info("Entering into Controller getAppointmentsByDoctorAndDateRange with DNI {}",dni);
        return docAppointmentService.getAppointmentsByDoctorAndDateRange(dni, startDate, endDate);
    }
}
