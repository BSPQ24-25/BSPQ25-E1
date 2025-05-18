package com.hospital.portal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a specialty in the hospital portal system.
 * <p>
 * This class is mapped to the "specialties" table in the database.
 * </p>
 */
@Entity
@Table(name = "specialties")
public class Specialty {

    /**
     * Unique identifier for the specialty.
     */
    @Id
    private String name;
    private String description;

    /**
     * Default constructor for JPA.
     */
    public Specialty() {}

    /**
     * Constructs a Specialty with the specified name and description.
     *
     * @param name the name of the specialty
     * @param description the description of the specialty
     */
    public Specialty(String name, String description) {
        this.name = name;
        this.description = description;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the Specialty.
     *
     * @return a string representation of the Specialty
     */
    @Override
    public String toString() {
        return "Specialty{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
