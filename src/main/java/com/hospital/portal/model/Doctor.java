package com.hospital.portal.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "doctors")
public class Doctor extends User {
    private String doctorId;
    @ManyToOne
    @JoinColumn(name = "specialty_name", referencedColumnName = "name")
    private Specialty specialty;

    public Doctor() {}

    public Doctor(String id, String name, String surname, LocalDate birthDate, String gender, String phone, String mail, String password, String doctorId, Specialty specialty) {
        super(id, name, surname, birthDate, gender, phone, mail, password);
        this.doctorId = doctorId;
        this.specialty = specialty;
    }

    // G&S
}
