
package com.hospital.portal.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.portal.controller.RegisterController;
import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.PatientRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



@Service
public class PatientService {
    private static final Logger logger = LogManager.getLogger(PatientService.class);


    @Autowired
    private PatientRepository patientRepository;

    /**
     * @brief Finds the patient by the DNI
     * 
     * @param SNI of the patient we want to find
     * 
     * @return A patient with the specified DNI
    */
    public Patient findPatientByDni(String dni) {
        logger.info("Finding patient with DNI: {}", dni);

        Optional<Patient> patientOpt = patientRepository.findByDni(dni);
        return patientOpt.orElse(null);
    }
    
}
