package com.hospital.portal.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "admins")
public class Admin extends User {
    @Column(nullable = false)
    private String workerId;

    // Default constructor
    public Admin() {}

    // Constructor with parameters
    public Admin(String dni, String name, String surname, LocalDate birthDate, String gender, String phone, String mail, String password, String workerId) {
        super(dni, name, surname, birthDate, gender, phone, mail, password);  // Call to the parent constructor
        this.workerId = workerId;
    }

    // Getter and Setter for workerId
    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    // Overridden toString method to include the new attribute "workerId"
    @Override
    public String toString() {
        return super.toString() +  // Call the toString method from the parent class (User)
               ", workerId='" + workerId + '\'' +  // Add the new attribute to the string representation
               '}';
    }
}
