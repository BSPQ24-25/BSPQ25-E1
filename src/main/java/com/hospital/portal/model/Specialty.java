package com.hospital.portal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "specialties")
public class Specialty {
    @Id
    private String name;
    private String description;

    // Default constructor
    public Specialty() {}

    // Constructor with parameters
    public Specialty(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // toString method
    @Override
    public String toString() {
        return "Specialty{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
