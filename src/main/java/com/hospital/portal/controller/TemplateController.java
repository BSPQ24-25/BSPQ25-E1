package com.hospital.portal.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {
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

    @GetMapping("/doctorHome")
    public String showInicioDoctor() {
        return "doctorHome";
    }

    @GetMapping("/adminHome")
    public String showInicioAdmin() {
        return "adminHome";
    }
}
