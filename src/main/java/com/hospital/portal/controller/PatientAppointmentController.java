package com.hospital.portal.controller;

import org.springframework.web.bind.annotation.*;

import com.hospital.portal.model.Appointment;
import com.hospital.portal.service.PatientAppointmentService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientAppointmentController {

    private final PatientAppointmentService patientAppointmentService;

    public PatientAppointmentController(PatientAppointmentService patientAppointmentService){
        this.patientAppointmentService = patientAppointmentService;
    }

    @GetMapping("/{patientDNI}/appointments")
    public List<Appointment> getAppointmentsByPatientDNI(@PathVariable String patientDNI) {
        return patientAppointmentService.getAppointmentsByPatientDNI(patientDNI);
    }

    @GetMapping("/{patientDNI}/appointmentsByDate")
    public List<Appointment> getAppointmentsByDate(@PathVariable String patientDNI, @RequestParam LocalDate date) {
        return patientAppointmentService.getAppointmentsByDate(patientDNI, date);
    }

    @GetMapping("/{patientDNI}/appointmentsBySpecialty")
    public List<Appointment> getAppointmentByPatientDniAndDoctorSpecialtyName(@PathVariable String patientDNI, @RequestParam String specialty_name) {
        return patientAppointmentService.getAppointmentByPatientDniAndDoctorSpecialtyName(patientDNI, specialty_name);
    }

    @PostMapping("/{patientDNI}/appointments")
    public Appointment addAppointmentPatient(@PathVariable String patientDNI, @RequestBody Appointment appointment) {
        return patientAppointmentService.addAppointmentPatient(patientDNI, appointment);
    }

    @PutMapping("/appointments/{appointmentId}")
    public Appointment modifyAppointment(@PathVariable String appointmentId, @RequestBody Appointment modifiedAppointment){
        return patientAppointmentService.modifyAppointment(appointmentId, modifiedAppointment);
    }

    
    @DeleteMapping("/appointments/{appointmentId}")
    public void deleteAppointmentPatient(@PathVariable String appointmentId) {
        patientAppointmentService.deleteAppointmentPatient(appointmentId);
    }
    
}
