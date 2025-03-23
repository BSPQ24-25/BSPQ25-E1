package com.hospital.portal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
public class Patient extends User {
    private String patientId;

    public Patient() {}

    public Patient(String id, String name, String surname, LocalDate birthDate, String gender, String phone, String mail, String password, String patientId) {
        super(id, name, surname, birthDate, gender, phone, mail, password);
        this.patientId = patientId;
    }

    // G&S
}