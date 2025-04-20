package com.hospital.portal.controller;

import com.hospital.portal.model.Appointment;
import com.hospital.portal.service.DocAppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DocAppointmentController {

    private final DocAppointmentService docAppointmentService;

    public DocAppointmentController(DocAppointmentService docAppointmentService) {
        this.docAppointmentService = docAppointmentService;
    }

    @GetMapping("/{doctorId}/appointments")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable String doctorId) {
        return docAppointmentService.getAppointmentsByDoctor(doctorId);
    }

    @PostMapping("/{doctorId}/appointments")
    public Appointment createAppointment(@PathVariable String doctorId, @RequestBody Appointment appointment) {
        return docAppointmentService.createAppointment(doctorId, appointment);
    }

    @PutMapping("/appointments/{appointmentId}")
    public Appointment updateAppointment(@PathVariable String appointmentId, @RequestBody Appointment appointment) {
        return docAppointmentService.updateAppointment(appointmentId, appointment);
    }

    @DeleteMapping("/appointments/{appointmentId}")
    public void deleteAppointment(@PathVariable String appointmentId) {
        docAppointmentService.deleteAppointment(appointmentId);
    }
}
