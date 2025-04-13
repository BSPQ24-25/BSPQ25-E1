package com.hospital.portal.unit.TestControllers;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.ResponseEntity;

import com.hospital.portal.controller.LoginController;
import com.hospital.portal.service.LoginService;

class LoginControllerTest {

    private LoginService loginService;
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        loginService = mock(LoginService.class);  // Create mock of LoginService
        loginController = new LoginController(loginService);  // Initialize controller with mock service
    }

    @Test
    @DisplayName("Should return role, dni, name, and token when credentials are valid")
    void testLogin_Success() {
        Map<String, Object> userInfo = Map.of(
            "role", "ADMIN", 
            "dni", "12345678A", 
            "name", "John Doe", 
            "token", "some-token"
        );
        when(loginService.login("12345678A", "password123")).thenReturn(userInfo);

        ResponseEntity<?> response = loginController.login("12345678A", "password123");

        assertEquals(200, response.getStatusCode().value());  // Verify that the status code is 200
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();  // Cast to Map
        assertEquals(userInfo, responseBody);  // Verify that the response body matches the expected user info
    }

    @Test
    @DisplayName("Should return 401 when credentials are invalid")
    void testLogin_InvalidCredentials() {
        Map<String, Object> invalidUser = Map.of("role", "INVALID_CREDENTIALS");
        when(loginService.login("12345678A", "wrongpassword")).thenReturn(invalidUser);

        ResponseEntity<?> response = loginController.login("12345678A", "wrongpassword");

        assertEquals(401, response.getStatusCode().value());  // Code 401 for incorrect credentials
        String responseBody = (String) response.getBody();  // Cast to String
        assertEquals("Invalid credentials", responseBody);  // Check for the exact error message
    }

    @Test
    @DisplayName("Should return 400 when DNI or password is missing")
    void testLogin_MissingCredentials() {
        ResponseEntity<?> response1 = loginController.login(null, "password123");
        ResponseEntity<?> response2 = loginController.login("12345678A", null);

        assertEquals(400, response1.getStatusCode().value());  // Bad request for missing DNI
        String responseBody1 = (String) response1.getBody();  // Cast to String
        assertEquals("DNI and password are required.", responseBody1);  // Error message

        assertEquals(400, response2.getStatusCode().value());  // Bad request for missing password
        String responseBody2 = (String) response2.getBody();  // Cast to String
        assertEquals("DNI and password are required.", responseBody2);  // Error message
    }

    @Test
    @DisplayName("Should return 500 when service throws exception")
    void testLogin_ServiceException() {
        when(loginService.login(anyString(), anyString())).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<?> response = loginController.login("12345678A", "password123");

        assertEquals(500, response.getStatusCode().value());  // Code 500 for internal server error
        String responseBody = (String) response.getBody();  // Cast to String
        assertTrue(responseBody.contains("Login failed: Database error"));
    }
}