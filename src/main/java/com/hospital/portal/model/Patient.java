package com.hospital.portal.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

/**
 * Represents a patient in the hospital portal system.
 * Extends the {@link User} class and adds a unique patient ID.
 * <p>
 * This class is mapped to the "patients" table in the database.
 * </p>
 */
@Entity
@Table(name = "patients")
public class Patient extends User {
    /**
     * Unique identifier for the patient (random number).
     */
    @Column(nullable = false)
    private String patientId; 

    /**
     * Default constructor for JPA.
     */
    public Patient() {
        super();
    }

    /**
     * Constructs a Patient with the specified details.
     *
     * @param dni the DNI of the patient
     * @param name the name of the patient
     * @param surname the surname of the patient
     * @param birthDate the birth date of the patient
     * @param gender the gender of the patient
     * @param phone the phone number of the patient
     * @param mail the email address of the patient
     * @param password the password of the patient
     * @param patientId the unique patient ID
     */
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
