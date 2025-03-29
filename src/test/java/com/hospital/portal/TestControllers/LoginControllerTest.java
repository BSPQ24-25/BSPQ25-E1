package com.hospital.portal.TestControllers;

import com.hospital.portal.controller.LoginController;
import com.hospital.portal.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    private LoginService loginService;
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        loginService = mock(LoginService.class);  // Creamos un mock del servicio
        loginController = new LoginController(loginService);  // Usamos el constructor con el servicio mockeado
    }

    @Test
    @DisplayName("Should return role when credentials are valid")
    void testLogin_Success() {
        when(loginService.login("12345678A", "password123")).thenReturn("ADMIN");

        ResponseEntity<?> response = loginController.login("12345678A", "password123");

        assertEquals(200, response.getStatusCode().value());  // Verificamos código 200
        assertEquals(Map.of("role", "ADMIN"), response.getBody());  // Verificamos respuesta esperada
    }

    @Test
    @DisplayName("Should return 401 when credentials are invalid")
    void testLogin_InvalidCredentials() {
        when(loginService.login("12345678A", "wrongpassword")).thenReturn("INVALID_CREDENTIALS");

        ResponseEntity<?> response = loginController.login("12345678A", "wrongpassword");

        assertEquals(401, response.getStatusCode().value());  // Código 401 para credenciales incorrectas
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    @DisplayName("Should return 400 when DNI or password is missing")
    void testLogin_MissingCredentials() {
        ResponseEntity<?> response1 = loginController.login(null, "password123");
        ResponseEntity<?> response2 = loginController.login("12345678A", null);

        assertEquals(400, response1.getStatusCode().value());
        assertEquals("DNI and password are required.", response1.getBody());

        assertEquals(400, response2.getStatusCode().value());
        assertEquals("DNI and password are required.", response2.getBody());
    }

    @Test
    @DisplayName("Should return 500 when service throws exception")
    void testLogin_ServiceException() {
        when(loginService.login(anyString(), anyString())).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<?> response = loginController.login("12345678A", "password123");

        assertEquals(500, response.getStatusCode().value());  // Código 500 para errores internos
        assertTrue(response.getBody().toString().contains("Login failed: Database error"));
    }
}