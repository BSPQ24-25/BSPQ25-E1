package com.hospital.portal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "admins")
public class Admin extends User {
    private String workerId;

    public Admin() {}

    public Admin(String id, String name, String surname, LocalDate birthDate, String gender, String phone, String mail, String password, String workerId) {
        super(id, name, surname, birthDate, gender, phone, mail, password);
        this.workerId = workerId;
    }

    // G&S
}
