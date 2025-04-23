
package com.hospital.portal.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.PatientRepository;


@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient findPatientByDni(String dni) {
        Optional<Patient> patientOpt = patientRepository.findByDni(dni);
        return patientOpt.orElse(null);
    }
    
}
