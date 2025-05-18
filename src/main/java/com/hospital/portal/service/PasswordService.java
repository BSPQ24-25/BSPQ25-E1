package com.hospital.portal.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    
    private final PasswordEncoder passwordEncoder;

    /** 
     * @brief Constructor of the PasswordService class using a BCrypt Password Encoder
    */
    public PasswordService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    /** 
     * @brief Transforms the plain text password to a secure password, hashing it.
     * @param Plain password
     * @return Hashed password
    */
    public String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }
    
    /** 
     * @brief Verifies the password to see if it is equal to the hashed one
     * @param Plain password
     * @param Hashed password
    */
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
}