package com.hospital.portal.controller;

import com.hospital.portal.model.Appointment;
import com.hospital.portal.service.DocAppointmentService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DocAppointmentController {

    private final DocAppointmentService docAppointmentService;

    public DocAppointmentController(DocAppointmentService docAppointmentService) {
        this.docAppointmentService = docAppointmentService;
    }

    @GetMapping("/{dni}/appointments")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable String dni) {
        return docAppointmentService.getAppointmentsByDoctor(dni);
    }

    @PostMapping("/{dni}/appointments")
    public Appointment createAppointment(@PathVariable String dni, @RequestBody Appointment appointment) {
        return docAppointmentService.createAppointment(dni, appointment);
    }

    @PutMapping("/appointments/{appointmentId}")
    public Appointment updateAppointment(@PathVariable String appointmentId, @RequestBody Appointment appointment) {
        return docAppointmentService.updateAppointment(appointmentId, appointment);
    }

    @DeleteMapping("/appointments/{appointmentId}")
    public void deleteAppointment(@PathVariable String appointmentId) {
        docAppointmentService.deleteAppointment(appointmentId);
    }
    
    @GetMapping("/{dni}/appointments/filter")
    public List<Appointment> getAppointmentsByDoctorAndDateRange(@PathVariable String dni, @RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        return docAppointmentService.getAppointmentsByDoctorAndDateRange(dni, startDate, endDate);
    }
}
