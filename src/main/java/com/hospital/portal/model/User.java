package com.hospital.portal.model;
import java.time.LocalDate;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.persistence.Column;


@MappedSuperclass
public abstract class User {
    @Id
    @Column(nullable = false)
    @NotBlank(message = "DNI is required")
    @Pattern(regexp = "^\\d{8}[A-Za-z]$")
    public String dni;
    
    @Column(nullable = false)
    @NotBlank(message = "Name is required")
    private String name;
    
    @Column(nullable = false)
    @NotBlank(message = "Surname is required")
    private String surname;
    
    @Column(nullable = false)
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
    
    @Column(nullable = false)
    @NotBlank(message = "Gender is required")
    private String gender;
    
    @Column(nullable = false)
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?\\d{9,15}$", message = "Invalid phone number")
    private String phone;
    
    @Column(nullable = false, name = "mail")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String mail;
    
    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\W_]).{10,}$", 
             message = "Password must have: 10+ chars, 1 uppercase, 1 lowercase, 1 number, 1 special char")
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
