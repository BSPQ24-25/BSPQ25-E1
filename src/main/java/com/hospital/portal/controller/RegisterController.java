package com.hospital.portal.controller;
import com.hospital.portal.service.RegisterService;
import org.springframework.http.HttpStatus;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final RegisterService registerService;
    private static final Logger logger = LogManager.getLogger(RegisterController.class);


    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

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
            logger.error("Error during registration", e);
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
            
        }
    }
}
