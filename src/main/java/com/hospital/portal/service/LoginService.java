package com.hospital.portal.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(LoginService.class);

 
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PasswordService passwordService;

    /** 
     * @brief Function to log into the Healthcare application
     * @param DNI of the patient/doctor/admin that wants to log in (has to be registered)
     * @param Password associated to the DNI
     * @return Map with the DNI, name and role (admin, doctor, patient) of the user logged. Can return an error if there are invalid credentials
    */
    public Map<String, Object> login(String dni, String password) {
        logger.info("Find by ID with DNI: {}", dni);

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

    /** 
     * @brief Function to get the user information
     * @param DNI of the patient/doctor/admin 
     * @param Name of the user
     * @param Roel of the user
     * @return Map with all the information given + the session token
    */
    private Map<String, Object> getUserInfo(String dni, String name, String role) {
        logger.info("Get user info DNI: {}", dni);

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("dni", dni);
        userInfo.put("name", name);
        userInfo.put("role", role);

        String token = generateToken(dni, role);
        userInfo.put("token", token);

        return userInfo;
    }


    /** 
     * @brief Generates the session token using JWTS (Jason Web Token). It sets the subjedt with the DNI to know to who it belongs, then it adds an personalized role, we add the time when it was created and when it expires (1 hour), we sign it so it cannot be modified and then we generate the string. 
     * @param DNI of the patient/doctor/admin that wants to log in (has to be registered)
     * @param Role of the user
     * @return A token in a string format
    */
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
