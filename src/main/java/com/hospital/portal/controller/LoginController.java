package com.hospital.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hospital.portal.service.LoginService;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private final LoginService loginService;

    
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<?> login(
        @RequestParam("dni") String dni, 
        @RequestParam("password") String password) {
            
        if (dni == null || password == null) {
            return ResponseEntity.badRequest().body("DNI and password are required.");
        }

        try {
            Map<String, Object> userInfo = loginService.login(dni, password);
            if ("INVALID_CREDENTIALS".equals(userInfo.get("role"))) {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
            return ResponseEntity.ok(Map.of("role", userInfo.get("role"), "dni", userInfo.get("dni"), "name", userInfo.get("name"), "token", userInfo.get("token")));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }
}
