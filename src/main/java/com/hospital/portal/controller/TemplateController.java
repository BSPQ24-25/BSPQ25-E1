package com.hospital.portal.controller;

import com.hospital.portal.model.Patient;
import com.hospital.portal.service.PatientService;
import com.hospital.portal.model.Appointment;
import com.hospital.portal.service.PatientAppointmentService;
import com.hospital.portal.service.DocAppointmentService;
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

  @Autowired
  private DocAppointmentService docAppointmentService;

  private final ObjectMapper mapper;

  /** 
  * @brief Constructor of the TemplateController class, where we register the module for the dates 
  */
  public TemplateController() {
    this.mapper = new ObjectMapper();
    this.mapper.registerModule(new JavaTimeModule()); 
  }

  /** 
     * @brief Gets the index.html template
     * @return the name of the HTML template to render (index.html)
    */
  @GetMapping("/")
  public String showIndex() {
    return "index";
  }

  /** 
     * @brief Gets the registrationFrom.html template to register
     * @return the name of the HTML template to render (registrarionForm.html)
    */
  @GetMapping("/register")
  public String showRegistrationForm() {
    return "registrationForm";
  }

  /** 
     * @brief Gets the loginForm.html template to log in the page
     * @return the name of the HTML template to render (loginForm.html)
    */
  @GetMapping("/login")
  public String showLoginForm() {
    return "loginForm";
  }

  /** 
     * @brief Gets the homepage.html template
     * @return the name of the HTML template to render (homepage.html)
    */
  @GetMapping("/homepage")
  public String showInicio() {
    return "homepage";
  }

  /** 
     * @brief Gets the patientDataView.html template for a specific patient with their information after they log in
     * @param DNI of the patient
     * @param Model model to send data to the template
     * @return the name of the HTML template to render (patientDataView.html)
    */
  @GetMapping("/patient/{dni}")
  public String showInicioPaciente(@PathVariable("dni") String dni, Model model) {
    Patient patient = patientService.findPatientByDni(dni);
    model.addAttribute("patient", patient);
    return "patientDataView";
  }

  /** 
     * @brief Gets the doctorHome.html template for the doctors after they log in
     * @return the name of the HTML template to render (doctorHome.html)
    */
  @GetMapping("/doctorHome")
  public String showInicioDoctor() {
    return "doctorHome";
  }

  /** 
     * @brief Gets the adminHome.html template for the admin after they log in
     * @return the name of the HTML template to render (adminHome.html)
    */
  @GetMapping("/admin")
  public String showInicioAdmin() {
    return "adminHome";
  }

  /** 
     * @brief Gets the calendar with the appointments for a specific user in the patientAppointmentView.html template
     * @param DNI of the patient
     * @param Model model to send data to the template
     * @return the name of the HTML template to render (patientAppointmentView.html)
    */
  @GetMapping("patient/{patientId}/calendar")
  public String showAppointmentCalendar(@PathVariable String patientId, Model model) throws JsonProcessingException {
    Patient patient = patientService.findPatientByDni(patientId);
    List<Appointment> appointments = appointmentServiceP.getAppointmentsByPatientDNI(patientId);

    String appointmentsJson = mapper.writeValueAsString(appointments);

    model.addAttribute("patient", patient);
    model.addAttribute("appointmentsJson", appointmentsJson);
    model.addAttribute("appointments", appointments);
    return "patientAppointmentView";
  }

  /** 
     * @brief Gets the calendar with the appointments for a specific doctor in the docAppintmentView.html template
     * @param DNI of the doctor
     * @param Model to send data to the template
     * @return the name of the HTML template to render (docAppointmentView.html)
    */
  @GetMapping("doctor/{doctorId}/calendar")
  public String showDoctorAppointmentCalendar(@PathVariable String doctorId, Model model)
      throws JsonProcessingException {
    List<Appointment> appointments = docAppointmentService.getAppointmentsByDoctor(doctorId);

    String appointmentsJson = mapper.writeValueAsString(appointments);

    model.addAttribute("doctorId", doctorId);
    model.addAttribute("appointmentsJson", appointmentsJson);
    model.addAttribute("appointments", appointments);
    return "docAppointmentView";
  }
}
