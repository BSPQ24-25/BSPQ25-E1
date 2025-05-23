package com.hospital.portal.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.portal.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private final LoginService loginService;
    private static final Logger logger = LogManager.getLogger(LoginController.class);


    /** 
     * @brief Constructor of the LoginController class
    */
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /** 
     * @brief Function to log into the Healthcare application
     * @param DNI of the patient/doctor/admin that wants to log in (has to be registered)
     * @param Password associated to the DNI
     * @return Status of the Login: if it was successful or not (invalid credentials, something failed)
    */
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
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }
}
