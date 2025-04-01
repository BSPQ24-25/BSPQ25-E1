package com.hospital.portal.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "patients")
public class Patient extends User {
    @Column(nullable = false)
    private String patientId; // random number

    // Default constructor
    public Patient() {
        super();
    }

    // Constructor with parameters
    public Patient(String dni, String name, String surname, LocalDate birthDate, String gender, String phone, String mail, String password, String patientId) {
        super(dni, name, surname, birthDate, gender, phone, mail, password);
        this.patientId = patientId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setDni(String dni) {
        super.setDni(dni);
    }

    public void setEmail(String email) {
       super.setEmail(email);
    }
}   
