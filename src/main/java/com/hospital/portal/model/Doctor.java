package com.hospital.portal.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

/**
 * Represents a doctor user in the hospital portal system.
 * Extends the {@link User} class and adds a unique doctor ID and specialty.
 * <p>
 * This class is mapped to the "doctors" table in the database.
 * </p>
 */
@Entity
@Table(name = "doctors")
public class Doctor extends User {
    /**
     * Unique identifier for the doctor.
     */
    @Column(nullable = false)
    private String doctorId;

    /**
     * Specialty of the doctor.
     */
    @ManyToOne
    @JoinColumn(name = "specialty_name", referencedColumnName = "name")
    private Specialty specialty;

    /**
     * Default constructor for JPA.
     */
    public Doctor() {}

    /**
     * Constructs a Doctor with the specified details.
     *
     * @param dni the DNI of the doctor
     * @param name the name of the doctor
     * @param surname the surname of the doctor
     * @param birthDate the birth date of the doctor
     * @param gender the gender of the doctor
     * @param phone the phone number of the doctor
     * @param mail the email address of the doctor
     * @param password the password of the doctor
     * @param doctorId the unique doctor ID
     * @param specialty the specialty of the doctor
     */
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

    /**
     * Returns a string representation of the Doctor, including doctorId and specialty.
     *
     * @return a string representation of the Doctor
     */
    @Override
    public String toString() {
        return super.toString() + 
               ", doctorId='" + doctorId + '\'' +  
               ", specialty=" + specialty + 
               '}';
    }
}
