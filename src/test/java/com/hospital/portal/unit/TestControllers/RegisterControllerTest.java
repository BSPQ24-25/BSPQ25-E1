package com.hospital.portal.unit.TestControllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.hospital.portal.controller.RegisterController;
import com.hospital.portal.service.RegisterService;

public class RegisterControllerTest {

    @Mock
    private RegisterService registerService;

    private RegisterController registerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        registerController = new RegisterController(registerService);
    }

    @Test
    public void testRegisterPatient_ServiceException() {
        // Simulating incorrect input which will throw an exception
        String dni = "invalidDni";
        String name = "John";
        String surname = "Doe";
        String phone = "123456789";
        String email = "invalid@example.com";
        String birthDate = "invalid-date"; // Invalid date format
        String gender = "Male";
        String password = "password123";

        // Call the controller method
        ResponseEntity<?> response = registerController.registerPatient(dni, name, surname, phone, email, birthDate, gender, password);

        // Assert that the response is a BAD_REQUEST (400) and contains the error message
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Registration failed"));
    }

    @Test
    public void testRegisterPatient_Success() {
        // Simulating successful registration
        String dni = "12345678X";
        String name = "John";
        String surname = "Doe";
        String phone = "123456789";
        String email = "john@example.com";
        String birthDate = "1990-01-01";
        String gender = "Male";
        String password = "password123";

        // Mock the service to return a successful message
        when(registerService.registerPatient(anyString(), anyString(), anyString(), anyString(), anyString(), any(), anyString(), anyString()))
                .thenReturn("Registration successful");

        // Call the controller method
        ResponseEntity<?> response = registerController.registerPatient(dni, name, surname, phone, email, birthDate, gender, password);

        // Assert that the response is OK (200) and contains the success message
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Registration successful"));

        // Verify that the service method was called once
        verify(registerService, times(1)).registerPatient(anyString(), anyString(), anyString(), anyString(), anyString(), any(), anyString(), anyString());
    }

    @Test
    public void testRegisterPatient_DniAlreadyExists() {
        // Simulating that the DNI is already registered
        String dni = "12345678X";
        String name = "John";
        String surname = "Doe";
        String phone = "123456789";
        String email = "john@example.com";
        String birthDate = "1990-01-01";
        String gender = "Male";
        String password = "password123";

        // Mock the service to return a conflict message if the DNI is already registered
        when(registerService.registerPatient(anyString(), anyString(), anyString(), anyString(), anyString(), any(), anyString(), anyString()))
                .thenReturn("DNI already registered");

        // Call the controller method
        ResponseEntity<?> response = registerController.registerPatient(dni, name, surname, phone, email, birthDate, gender, password);

        // Assert that the response status is CONFLICT (409) and contains the conflict message
        assertEquals(409, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("DNI already registered"));
    }
}
