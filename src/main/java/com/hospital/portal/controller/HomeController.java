package com.hospital.portal.controller;

import com.hospital.portal.model.Admin;
import com.hospital.portal.model.Doctor;
import com.hospital.portal.model.Patient;
import com.hospital.portal.repository.AdminRepository;
import com.hospital.portal.repository.DoctorRepository;
import com.hospital.portal.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/")
    public String home() {
        List<Doctor> doctors = doctorRepository.findAll();
        List<Admin> admins = adminRepository.findAll();
        List<Patient> patients = patientRepository.findAll();

        StringBuilder response = new StringBuilder();

        response.append("<h1>All Users</h1>");

        response.append("<h2>Doctors:</h2>");
        for (Doctor doctor : doctors) {
            response.append("<p>").append(doctor.getName()).append("</p>");
        }

        response.append("<h2>Admins:</h2>");
        for (Admin admin : admins) {
            response.append("<p>").append(admin.getName()).append("</p>");
        }

        response.append("<h2>Patients:</h2>");
        for (Patient patient : patients) {
            response.append("<p>").append(patient.getName()).append("</p>");
        }

        return response.toString();
    }
}
