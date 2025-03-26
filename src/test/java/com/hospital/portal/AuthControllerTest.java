package com.hospital.portal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.portal.controller.AuthController;
import com.hospital.portal.dto.LoginRequest;
import com.hospital.portal.dto.PatientRegistrationRequest;
import com.hospital.portal.model.Patient;
import com.hospital.portal.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(AuthControllerTest.TestSecurityConfig.class) //  Importamos la configuración de seguridad para deshabilitarla
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void registerPatient_ValidRequest_ReturnsOk() throws Exception {
        PatientRegistrationRequest request = new PatientRegistrationRequest();
        request.setDni("12345678A");
        request.setName("John");
        request.setSurname("Doe");
        request.setBirthDate(LocalDate.of(1990, 1, 15));
        request.setGender("Male");
        request.setPhone("612345678");
        request.setPassword("SecurePass123");

        Patient patient = new Patient();
        patient.setId(request.getDni());
        patient.setName(request.getName());

        when(authService.registerPatient(any(PatientRegistrationRequest.class))).thenReturn(patient);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(request.getDni()))
                .andExpect(jsonPath("$.name").value(request.getName()));
    }

    @Test
    public void registerPatient_InvalidRequest_ReturnsBadRequest() throws Exception {
        PatientRegistrationRequest invalidRequest = new PatientRegistrationRequest();
        invalidRequest.setDni("short"); // DNI inválido

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void login_ValidCredentials_ReturnsOk() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setDni("12345678A");
        request.setPassword("SecurePass123");

        Patient patient = new Patient();
        patient.setId(request.getDni());
        patient.setName("John");

        when(authService.authenticate(any(LoginRequest.class))).thenReturn(patient);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(request.getDni()));
    }

    //Configuración de seguridad para deshabilitar autenticación en los tests
    @Configuration
    public static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(csrf -> csrf.disable())  // 🔥 Desactiva CSRF
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())  // 🔥 Permite todas las solicitudes
                    .build();
        }
    }
}
