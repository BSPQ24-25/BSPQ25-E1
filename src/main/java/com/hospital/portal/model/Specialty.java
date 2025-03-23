package com.hospital.portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "specialties")
public class Specialty {
    @Id
    private String name;
    private String description;

    public Specialty() {}

    public Specialty(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // G&S
}
