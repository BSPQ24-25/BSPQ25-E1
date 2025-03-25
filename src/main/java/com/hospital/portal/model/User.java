package com.hospital.portal.model;
import java.time.LocalDate;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

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

    // Default constructor
    public User() {}

    // Constructor with parameters
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

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString method
    @Override
    public String toString() {
        return "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", surname='" + surname + '\'' +
               ", birthDate=" + birthDate +
               ", gender='" + gender + '\'' +
               ", phone='" + phone + '\'' +
               ", mail='" + mail + '\'' +
               ", password='" + password + '\'' ;
    }
}
