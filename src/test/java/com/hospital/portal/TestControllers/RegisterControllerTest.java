package com.hospital.portal.TestControllers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.http.ResponseEntity;

import com.hospital.portal.controller.RegisterController;
import com.hospital.portal.service.RegisterService;

class RegisterControllerTest {

    private RegisterService registerService;
    private RegisterController registerController;

    @BeforeEach
    void setUp() {
        registerService = Mockito.mock(RegisterService.class);
        registerController = new RegisterController(registerService);
    }

    @Test
    void testRegisterPatient_ServiceException() {
        when(registerService.registerPatient(
                anyString(), anyString(), anyString(), anyString(), anyString(), any(LocalDate.class), anyString(), anyString()
        )).thenThrow(new RuntimeException("Database error"));

        // use methodo of controller
        ResponseEntity<?> response = registerController.registerPatient(
                "12345678A", "John", "Doe", "987654321", "john.doe@example.com", "1980-05-10", "M", "password123"
        );

        // Verify it returns for bad request 400
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Register failed: Database error"));
    }

    @Test
    void testRegisterPatient_Success() {
        // Simulate succesfull register!
        when(registerService.registerPatient(
                anyString(), anyString(), anyString(), anyString(), anyString(), any(LocalDate.class), anyString(), anyString()
        )).thenReturn("Registration successful");

        ResponseEntity<?> response = registerController.registerPatient(
                "12345678A", "John", "Doe", "987654321", "john.doe@example.com", "1980-05-10", "M", "password123"
        );

        // Verifty return code is 200 for success
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Registration successful"));
    }
}
