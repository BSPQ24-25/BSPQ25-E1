package com.hospital.portal.unit.TestServices;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hospital.portal.service.PasswordService;


class PasswordServiceTest {

    private PasswordService passwordService;
    private PasswordEncoder passwordEncoder;

    // We use the constructor to create the objects

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        passwordService = new PasswordService();
    }

    // We use a test to verify the hash of a password,

    @Test
    void testHashPassword() {
        String rawPassword = "securePassword123";
        String hashedPassword = passwordService.hashPassword(rawPassword);

        assertNotNull(hashedPassword);
        assertNotEquals(rawPassword, hashedPassword); // Debe ser diferente al original
    }

    // We use a test to verify the hash of a password,  it is not valid if the password is not equal to the password.

    @Test
    void testVerifyPassword() {
        String rawPassword = "securePassword123";
        String hashedPassword = passwordService.hashPassword(rawPassword);

        assertTrue(passwordService.verifyPassword(rawPassword, hashedPassword));
        assertFalse(passwordService.verifyPassword("wrongPassword", hashedPassword));
    }
}
