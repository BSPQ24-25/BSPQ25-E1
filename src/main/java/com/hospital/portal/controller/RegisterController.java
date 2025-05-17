package com.hospital.portal.controller;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.portal.service.RegisterService;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final RegisterService registerService;
    private static final Logger logger = LogManager.getLogger(RegisterController.class);

    /** 
     * @brief Constructor of the RegisterController class
    */
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    /**
     * @brief Registers into the database a new patient
     * @param DNI of the new patient
     * @param Name of the new patient
     * @param Surname of the new patient
     * @param Phone number of the new patient
     * @param email address of the new patient
     * @param birthdate of the new patient
     * @param Gender of the new patient
     * @param Password of the account of the new patient
     * @return Status of the Registration: if it was successful or not (already existing user, something failed)
    */
    @PostMapping
    public ResponseEntity<?> registerPatient(
            @RequestParam("dni") String dni,
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("birthDate") String birthDateString,
            @RequestParam("gender") String gender,
            @RequestParam("password") String password) {
        try {
            LocalDate birthDate = LocalDate.parse(birthDateString);
            logger.info("Registering patient with DNI: {}", dni);
            String result = registerService.registerPatient(
                    dni, name, surname, phone, email, birthDate, gender, password);

            // Check if the result contains an error message
            if (result.contains("already registered")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
            }

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
            
        }
    }
}
