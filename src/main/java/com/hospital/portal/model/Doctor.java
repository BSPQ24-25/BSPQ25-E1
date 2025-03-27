package com.hospital.portal.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "doctors")
public class Doctor extends User {
    @Column(nullable = false)
    private String doctorId;

    @ManyToOne
    @JoinColumn(name = "specialty_name", referencedColumnName = "name")
    private Specialty specialty;

    // Default constructor
    public Doctor() {}

    // Constructor with parameters
    public Doctor(String dni, String name, String surname, LocalDate birthDate, String gender, String phone, String mail, String password, String doctorId, Specialty specialty) {
        super(dni, name, surname, birthDate, gender, phone, mail, password);  // Call to the parent constructor
        this.doctorId = doctorId;
        this.specialty = specialty;
    }

    // Getter and Setter for doctorId
    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    // Getter and Setter for specialty
    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    // Overridden toString method to include the new attributes "doctorId" and "specialty"
    @Override
    public String toString() {
        return super.toString() +  // Call the toString method from the parent class (User)
               ", doctorId='" + doctorId + '\'' +  // Add the new attribute doctorId
               ", specialty=" + specialty +  // Add the new attribute specialty
               '}';
    }
}
