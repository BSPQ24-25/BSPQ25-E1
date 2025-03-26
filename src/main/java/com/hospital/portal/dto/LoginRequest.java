package com.hospital.portal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for login requests
 */
public class LoginRequest {
    @NotBlank(message = "DNI is required")
    @Size(min = 9, max = 9, message = "DNI must be exactly 9 characters")
    private String dni;

    @NotBlank(message = "Password is required")
    private String password;

    // Getters and Setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}