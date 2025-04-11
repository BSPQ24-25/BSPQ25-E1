package com.hospital.portal.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.portal.model.Admin;
import com.hospital.portal.model.Doctor;
import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.AdminRepository;
import com.hospital.portal.repository.DoctorRepository;
import com.hospital.portal.repository.PatientRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class LoginService {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
 
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PasswordService passwordService;

    public Map<String, Object> login(String dni, String password) {
        Optional<Admin> admin = adminRepository.findById(dni);
        if (admin.isPresent() && passwordService.verifyPassword(password, admin.get().getPassword())) {
            return getUserInfo(admin.get().getDni(), admin.get().getName(), "ADMIN");
        }

        Optional<Doctor> doctor = doctorRepository.findById(dni);
        if (doctor.isPresent() && passwordService.verifyPassword(password, doctor.get().getPassword())) {
            return getUserInfo(doctor.get().getDni(), doctor.get().getName(), "DOCTOR");
        }

        Optional<Patient> patient = patientRepository.findById(dni);
        if (patient.isPresent() && passwordService.verifyPassword(password, patient.get().getPassword())) {
            return getUserInfo(patient.get().getDni(), patient.get().getName(), "PATIENT");
        }

        return Map.of("role", "INVALID_CREDENTIALS");
    }

    private Map<String, Object> getUserInfo(String dni, String name, String role) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("dni", dni);
        userInfo.put("name", name);
        userInfo.put("role", role);

        String token = generateToken(dni, role);
        userInfo.put("token", token);

        return userInfo;
    }


    
    private String generateToken(String dni, String role) {

        return Jwts.builder()
                .setSubject(dni)  
                .claim("role", role) 
                .setIssuedAt(new Date())  
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))//1 hour 
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}
