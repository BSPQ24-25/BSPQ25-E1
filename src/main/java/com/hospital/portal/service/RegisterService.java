package com.hospital.portal.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.PatientRepository;

@Service
public class RegisterService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PasswordService passwordService;

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

    public String registerPatient(String dni, String name, String surname, String phone, String mail,
            LocalDate birthDate, String gender, String password) {

        if (patientRepository.existsByDni(dni) || patientRepository.existsByMail(mail)) {
            return "DNI or email are already registered!";
        }

        Patient newPatient = new Patient();
        newPatient.setDni(dni);
        newPatient.setName(name);
        newPatient.setSurname(surname);
        newPatient.setPhone(phone);
        newPatient.setEmail(mail);
        newPatient.setBirthDate(birthDate);
        newPatient.setGender(gender);
        newPatient.setPassword(passwordService.hashPassword(password));
        newPatient.setPatientId(generateIncrementalPatientId());

        patientRepository.saveAndFlush(newPatient);
        return "Registration successful";
    }
}
