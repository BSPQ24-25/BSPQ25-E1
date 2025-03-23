package com.hospital.portal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
public abstract class User {
    @Id
    private String id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String mail;
    private String password;

    public User() {}

    public User(String id, String name, String surname, LocalDate birthDate, String gender, String phone, String mail, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.mail = mail;
        this.password = password;
    }

    // G&S

    public String getName() {
        return this.name;
    }
}
