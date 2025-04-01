package com.hospital.portal.model;
import java.time.LocalDate;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;


@MappedSuperclass
public abstract class User {
    @Id
    @Column(nullable = false)
    public String dni; 
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String mail;
    @Column(nullable = false)
    private String password;

    // Default constructor
    public User() {}

    // Constructor with parameters
    public User(String dni, String name, String surname, LocalDate birthDate, String gender, String phone, String mail, String password) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.mail = mail;
        this.password = password;
    }

    // Getters and Setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getEmail() {
        return mail;
    }

    public void setEmail(String mail) {
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
        return "dni='" + dni + '\'' +
               ", name='" + name + '\'' +
               ", surname='" + surname + '\'' +
               ", birthDate=" + birthDate +
               ", gender='" + gender + '\'' +
               ", phone='" + phone + '\'' +
               ", mail='" + mail + '\'' +
               ", password='" + password + '\'' ;
    }
}
