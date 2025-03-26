package com.hospital.portal;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hospital.portal.dto.LoginRequest;

import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validRequest_NoValidationErrors() {
        LoginRequest request = new LoginRequest();
        request.setDni("12345678A");
        request.setPassword("password123");
        assertEquals(0, validator.validate(request).size());
    }

    @Test
    public void invalidDni_ValidationFails() {
        LoginRequest request = new LoginRequest();
        request.setDni("short");
        request.setPassword("password123");
        assertEquals(1, validator.validate(request).size());
    }

    @Test
    public void missingPassword_ValidationFails() {
        LoginRequest request = new LoginRequest();
        request.setDni("12345678A");
        request.setPassword(null);
        assertEquals(1, validator.validate(request).size());
    }
}