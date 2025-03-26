package com.hospital.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hospital.portal.service.LoginService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String dni = credentials.get("id");
        String password = credentials.get("password");

        String role = loginService.login(dni, password);

        if ("INVALID_CREDENTIALS".equals(role)) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        return ResponseEntity.ok(Map.of("role", role));
    }

}
