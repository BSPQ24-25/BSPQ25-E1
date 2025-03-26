package com.hospital.portal.service;

import com.hospital.portal.model.Admin;
import com.hospital.portal.model.Doctor;
import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.AdminRepository;
import com.hospital.portal.repository.DoctorRepository;
import com.hospital.portal.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private PatientRepository patientRepository;

    public String login(String dni, String password) {
        Optional<Admin> admin = adminRepository.findById(dni);
        if (admin.isPresent() && password.equals(admin.get().getPassword())) {
            return "ADMIN";
        }

        Optional<Doctor> doctor = doctorRepository.findById(dni);
        if (doctor.isPresent() && password.equals(doctor.get().getPassword())) {
            return "DOCTOR";
        }

        Optional<Patient> patient = patientRepository.findById(dni);
        if (patient.isPresent() && password.equals(patient.get().getPassword())) {
            return "PATIENT";
        }

        return "INVALID_CREDENTIALS";
    }
}
