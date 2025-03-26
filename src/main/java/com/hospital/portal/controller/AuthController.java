package com.hospital.portal.controller;

import com.hospital.portal.dto.LoginRequest;
import com.hospital.portal.dto.PatientRegistrationRequest;
import com.hospital.portal.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@Valid @RequestBody PatientRegistrationRequest request) {
        try {
            return ResponseEntity.ok(authService.registerPatient(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Login information not valid");
        }
        try {
            Object user = authService.authenticate(request);
            System.out.println("Login successful for DNI: " + request.getDni());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            System.out.println("Login failed for DNI: " + request.getDni() + " - " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}