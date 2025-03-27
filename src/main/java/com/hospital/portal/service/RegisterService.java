package com.hospital.portal.service;

import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class RegisterService {
    @Autowired
    private PatientRepository patientRepository;

    public String generateIncrementalPatientId() {
        // Find the last patient to get the maximum current patient ID
        Optional<Patient> lastPatient = patientRepository.findTopByOrderByPatientIdDesc();
        
        if (lastPatient.isEmpty()) {
            // If no patients exist, start with P0001
            return "P0001";
        }
        
        String lastPatientId = lastPatient.get().getPatientId();
        // Remove the 'P' and parse the number
        int currentNumber = Integer.parseInt(lastPatientId.substring(1));
        
        // Increment and format the new patient ID
        return String.format("P%04d", currentNumber + 1);
    }

    public String registerPatient(String dni, String name, String surname, String phone, String email, LocalDate birthDate, String gender, String password) {
        if (patientRepository.existsById(dni)) {
            return "DNI already registered";
        }
        
        Patient newPatient = new Patient();
        newPatient.setDni(dni);
        newPatient.setName(name);
        newPatient.setSurname(surname);
        newPatient.setPhone(phone);
        newPatient.setEmail(email);
        newPatient.setBirthDate(birthDate);
        newPatient.setGender(gender);
        newPatient.setPassword(password);
        
        // Generate incremental patient ID
        String newPatientId = generateIncrementalPatientId();
        newPatient.setPatientId(newPatientId);
        
        patientRepository.save(newPatient);
        return "Registration successful";
    }
}
