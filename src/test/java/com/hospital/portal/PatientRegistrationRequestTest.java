package com.hospital.portal;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hospital.portal.dto.PatientRegistrationRequest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PatientRegistrationRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validRequest_NoValidationErrors() {
        PatientRegistrationRequest request = createValidRequest();
        assertEquals(0, validator.validate(request).size());
    }

    @Test
    public void invalidDni_ValidationFails() {
        PatientRegistrationRequest request = createValidRequest();
        request.setDni("short");
        assertEquals(1, validator.validate(request).size());
    }

    @Test
    public void missingName_ValidationFails() {
        PatientRegistrationRequest request = createValidRequest();
        request.setName(null);
        assertEquals(1, validator.validate(request).size());
    }

    @Test
    public void invalidPhone_ValidationFails() {
        PatientRegistrationRequest request = createValidRequest();
        request.setPhone("123");
        assertEquals(1, validator.validate(request).size());
    }

    private PatientRegistrationRequest createValidRequest() {
        PatientRegistrationRequest request = new PatientRegistrationRequest();
        request.setDni("12345678A");
        request.setName("John");
        request.setSurname("Doe");
        request.setBirthDate(LocalDate.of(1990, 1, 1));
        request.setGender("Male");
        request.setPhone("612345678");
        request.setPassword("Secure123");
        return request;
    }
}