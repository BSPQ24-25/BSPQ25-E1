package com.hospital.portal.controller;

import com.hospital.portal.model.Patient;
import com.hospital.portal.service.PatientService;
import com.hospital.portal.model.Appointment;
import com.hospital.portal.service.PatientAppointmentService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // Importante para fechas
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TemplateController {
  @Autowired
  private PatientService patientService;

  @Autowired
  private PatientAppointmentService appointmentServiceP;

  private final ObjectMapper mapper;

  public TemplateController() {
    this.mapper = new ObjectMapper();
    this.mapper.registerModule(new JavaTimeModule()); // Registramos el módulo para fechas
  }

  @GetMapping("/")
  public String showIndex() {
    return "index";
  }

  @GetMapping("/register")
  public String showRegistrationForm() {
    return "registrationForm";
  }

  @GetMapping("/login")
  public String showLoginForm() {
    return "loginForm";
  }

  @GetMapping("/homepage")
  public String showInicio() {
    return "homepage";
  }

  @GetMapping("/patient/{dni}")
  public String showInicioPaciente(@PathVariable("dni") String dni, Model model) {
    Patient patient = patientService.findPatientByDni(dni);
    model.addAttribute("patient", patient);
    return "patientDataView";
  }

  @GetMapping("/doctorHome")
  public String showInicioDoctor() {
    return "doctorHome";
  }

  @GetMapping("/adminHome")
  public String showInicioAdmin() {
    return "adminHome";
  }

  @GetMapping("/{patientId}/calendar")
  public String showAppointmentCalendar(@PathVariable String patientId, Model model) throws JsonProcessingException {
    Patient patient = patientService.findPatientByDni(patientId);
    List<Appointment> appointments = appointmentServiceP.getAppointmentsByPatientDNI(patientId);

    String appointmentsJson = mapper.writeValueAsString(appointments);

    model.addAttribute("patient", patient);
    model.addAttribute("appointmentsJson", appointmentsJson);
    model.addAttribute("appointments", appointments);
    return "patientAppointmentView";
  }
}
