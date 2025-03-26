package com.hospital.portal.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients")
public class Patient extends User {
    private String patientId; //numero aleatorio

    // Default constructor
    public Patient() {}

    // Constructor with parameters
    public Patient(String id, String name, String surname, LocalDate birthDate, String gender, String phone, String mail, String password, String patientId) {
        super(id, name, surname, birthDate, gender, phone, mail, password);
        this.patientId = patientId;
    }

    // Getter and Setter for patientId
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }


    // toString method
    @Override
    public String toString() {
        return "Patient{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", birthDate=" + getBirthDate() +
                ", gender='" + getGender() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", mail='" + getMail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", patientId='" + patientId + '\'' +
                '}';
    }

}
