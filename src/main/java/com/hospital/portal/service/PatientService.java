
package com.hospital.portal.service;

import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import com.hospital.portal.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import com.hospital.portal.service.PatientService;


@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient findPatientByDni(String dni) {
        Optional<Patient> patientOpt = patientRepository.findByDni(dni);
        return patientOpt.orElse(null);
    }
    
}
