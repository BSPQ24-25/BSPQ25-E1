package com.hospital.portal.service;

import com.hospital.portal.dto.LoginRequest;
import com.hospital.portal.dto.PatientRegistrationRequest;
import com.hospital.portal.model.*;
import com.hospital.portal.repository.*;

import jakarta.transaction.Transactional;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service handling authentication and user registration
 */
@Service
public class AuthService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new patient
     * 
     * @param request Patient registration data
     * @return Registered patient
     * @throws RuntimeException if patient already exists
     */
    @Transactional
    public Patient registerPatient(PatientRegistrationRequest request) {
        // Verifica si el DNI ya existe
        if (patientRepository.existsById(request.getDni())) {
            throw new RuntimeException("El DNI ya está registrado");
        }

        // Crea el nuevo paciente
        Patient patient = new Patient();
        patient.setId(request.getDni());
        String shortUuid = UUID.randomUUID().toString().substring(0, 8);
        patient.setPatientId(shortUuid);

        // Setea los demás campos
        patient.setName(request.getName());
        patient.setSurname(request.getSurname());
        patient.setBirthDate(request.getBirthDate());
        patient.setGender(request.getGender());
        patient.setMail(request.getEmail());
        patient.setPhone(request.getPhone());
        patient.setPassword(passwordEncoder.encode(request.getPassword()));

        return patientRepository.save(patient);
    }

    /**
     * Authenticates a user
     * 
     * @param request Login credentials
     * @return Authenticated user (Patient, Doctor, or Admin)
     * @throws RuntimeException if authentication fails
     */
    public Object authenticate(LoginRequest request) {
        // Check which table contains the user
        if (adminRepository.existsById(request.getDni())) {
            Admin admin = adminRepository.findById(request.getDni())
                    .orElseThrow(() -> new RuntimeException("Admin not found"));
            if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
                throw new RuntimeException("Incorrect password");
            }
            return admin;
        } else if (doctorRepository.existsById(request.getDni())) {
            Doctor doctor = doctorRepository.findById(request.getDni())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            if (!passwordEncoder.matches(request.getPassword(), doctor.getPassword())) {
                throw new RuntimeException("Incorrect password");
            }
            return doctor;
        } else if (patientRepository.existsById(request.getDni())) {
            Patient patient = patientRepository.findById(request.getDni())
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            if (!passwordEncoder.matches(request.getPassword(), patient.getPassword())) {
                throw new RuntimeException("Incorrect password");
            }
            return patient;
        }
        throw new RuntimeException("User not found");
    }
}