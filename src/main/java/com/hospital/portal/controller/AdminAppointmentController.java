package com.hospital.portal.controller;

import com.hospital.portal.model.Appointment;
import com.hospital.portal.model.Doctor;
import com.hospital.portal.model.Patient;
import com.hospital.portal.service.AdminAppointmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminAppointmentController {

    private static final Logger logger = LogManager.getLogger(AdminAppointmentController.class);

    private final AdminAppointmentService adminAppointmentService;

    public AdminAppointmentController(AdminAppointmentService adminAppointmentService) {
        this.adminAppointmentService = adminAppointmentService;
    }

    @PostMapping("/appointments")
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        logger.info("Admin is creating a new appointment");
        return adminAppointmentService.createAppointment(appointment);
    }

    @PutMapping("/appointments/{appointmentId}")
    public Appointment updateAppointment(@PathVariable String appointmentId, @RequestBody Appointment updatedAppointment) {
        logger.info("Admin is updating appointment with ID {}", appointmentId);
        return adminAppointmentService.updateAppointment(appointmentId, updatedAppointment);
    }

    @DeleteMapping("/appointments/{appointmentId}")
    public void deleteAppointment(@PathVariable String appointmentId) {
        logger.info("Admin is deleting appointment with ID {}", appointmentId);
        adminAppointmentService.deleteAppointment(appointmentId);
    }

    @GetMapping("/doctors")
    public List<Doctor> getAllDoctors() {
        logger.info("Admin requested all doctors for dropdown");
        return adminAppointmentService.getAllDoctors();
    }

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        logger.info("Admin requested all patients for dropdown");
        return adminAppointmentService.getAllPatients();
    }
    
    @GetMapping("/appointments")
    public List<Appointment> getAllAppointments() {
        logger.info("Admin requested all appointments in the system");
        return adminAppointmentService.getAllAppointments();
    }
}
