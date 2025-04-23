package com.hospital.portal.controller;

import com.hospital.portal.model.Patient;
import com.hospital.portal.service.PatientService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TemplateController {
    @Autowired
    private PatientService patientService;

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
}
