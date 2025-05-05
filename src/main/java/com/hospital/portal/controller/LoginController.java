package com.hospital.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hospital.portal.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private final LoginService loginService;
    private static final Logger logger = LogManager.getLogger(LoginController.class);


    
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
                logger.error("Invalid credential during registration");

                return ResponseEntity.status(401).body("Invalid credentials");
            }
            logger.info("Log in user with DNI: {}", dni);
            return ResponseEntity.ok(Map.of("role", userInfo.get("role"), "dni", userInfo.get("dni"), "name", userInfo.get("name"), "token", userInfo.get("token")));
        } catch (Exception e) {
            logger.error("Error during log in", e);
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }
}
