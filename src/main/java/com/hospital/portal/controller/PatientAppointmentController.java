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
import com.hospital.portal.service.PatientAppointmentService;


@RestController
@RequestMapping("/api/patients")
public class PatientAppointmentController {


    private final PatientAppointmentService patientAppointmentService;
    private static final Logger logger = LogManager.getLogger(PatientAppointmentController.class);


    public PatientAppointmentController(PatientAppointmentService patientAppointmentService){
        this.patientAppointmentService = patientAppointmentService;
    }

    @GetMapping("/{patientDNI}/appointments")
    public List<Appointment> getAppointmentsByPatientDNI(@PathVariable String patientDNI) {
        logger.info("Entering into Controller getAppointmentsByPatientDNI with DNI {}",patientDNI);

        return patientAppointmentService.getAppointmentsByPatientDNI(patientDNI);
    }

    @GetMapping("/{patientDNI}/appointmentsByDate")
    public List<Appointment> getAppointmentsByDate(@PathVariable String patientDNI, @RequestParam LocalDate date) {
        logger.info("Entering into Controller getAppointmentsByDate with DNI {}",patientDNI);

        return patientAppointmentService.getAppointmentsByDate(patientDNI, date);
    }

    @GetMapping("/{patientDNI}/appointmentsBySpecialty")
    public List<Appointment> getAppointmentByPatientDniAndDoctorSpecialtyName(@PathVariable String patientDNI, @RequestParam String specialty_name) {
        logger.info("Entering into Controller getAppointmentByPatientDniAndDoctorSpecialtyName with DNI",patientDNI);

        return patientAppointmentService.getAppointmentByPatientDniAndDoctorSpecialtyName(patientDNI, specialty_name);
    }

    @PostMapping("/{patientDNI}/appointments")
    public Appointment addAppointmentPatient(@PathVariable String patientDNI, @RequestBody Appointment appointment) {
        logger.info("Entering into Controller addAppointmentPatient with DNI {}",patientDNI);

        return patientAppointmentService.addAppointmentPatient(patientDNI, appointment);
    }

    @PutMapping("/appointments/{appointmentId}")
    public Appointment modifyAppointment(@PathVariable String appointmentId, @RequestBody Appointment modifiedAppointment){
        logger.info("Entering into Controller modifyAppointment with appointment ID {}",appointmentId);

        return patientAppointmentService.modifyAppointment(appointmentId, modifiedAppointment);
    }

    
    @DeleteMapping("/appointments/{appointmentId}")
    public void deleteAppointmentPatient(@PathVariable String appointmentId) {
        logger.info("Entering into Controller deleteAppointmentPatient  with appointment ID {}",appointmentId);

        patientAppointmentService.deleteAppointmentPatient(appointmentId);
    }
    
}
