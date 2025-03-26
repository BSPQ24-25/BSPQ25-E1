package com.hospital.portal.service;

import com.hospital.portal.repository.AdminRepository;
import com.hospital.portal.repository.DoctorRepository;
import com.hospital.portal.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private PatientRepository patientRepository;

    public String determineUserRole(String dni) {
        if (adminRepository.existsById(dni)) {
            return "ADMIN";
        } else if (doctorRepository.existsById(dni)) {
            return "DOCTOR";
        } else if (patientRepository.existsById(dni)) {
            return "PATIENT";
        }
        return null;
    }
}