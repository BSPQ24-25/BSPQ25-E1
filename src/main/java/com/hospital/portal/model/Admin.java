package com.hospital.portal.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

/**
 * Represents an administrator user in the hospital portal system.
 * Extends the {@link User} class and adds a unique worker ID.
 * <p>
 * This class is mapped to the "admins" table in the database.
 * </p>
 */
@Entity
@Table(name = "admins")
public class Admin extends User {

     /**
     * Unique identifier for the admin worker.
     */
    @Column(nullable = false)
    private String workerId;

    /**
     * Default constructor for JPA.
     */    
    public Admin() {}

    /**
     * Constructs an Admin with the specified details.
     *
     * @param dni the DNI of the admin
     * @param name the name of the admin
     * @param surname the surname of the admin
     * @param birthDate the birth date of the admin
     * @param gender the gender of the admin
     * @param phone the phone number of the admin
     * @param mail the email address of the admin
     * @param password the password of the admin
     * @param workerId the unique worker ID of the admin
     */
    public Admin(String dni, String name, String surname, LocalDate birthDate, String gender, String phone, String mail, String password, String workerId) {
        super(dni, name, surname, birthDate, gender, phone, mail, password);  // Call to the parent constructor
        this.workerId = workerId;
    }

    
    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    /**
     * Returns a string representation of the Admin, including the worker ID.
     *
     * @return a string representation of the Admin
     */
    @Override
    public String toString() {
        return super.toString() +  
               ", workerId='" + workerId + '\'' +  
               '}';
    }
}
