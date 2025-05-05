package com.hospital.portal.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.portal.repository.AdminRepository;
import com.hospital.portal.repository.DoctorRepository;
import com.hospital.portal.repository.PatientRepository;


@Service
public class RoleService {
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private PatientRepository patientRepository;

    private static final Logger logger = LogManager.getLogger(RoleService.class);


    public String determineUserRole(String dni) {
        logger.info("Determining role for patient with DNI: {}", dni);

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