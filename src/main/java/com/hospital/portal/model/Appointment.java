package com.hospital.portal.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    public String appointmentId;

    @Column(nullable = false)
    @NotNull(message = "Date is required")
    @FutureOrPresent(message = "Date must be in the present or future")
    private LocalDate date;

    @Column(nullable = false)
    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @Column(nullable = false)
    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull(message = "Patient id is required")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor is required")
    private Doctor doctor;

    @Column(length = 1000)
    private String appointmentPurpose ; //Motive of the appointment

    @Column(length = 1000)
    private String observations; //Doctor's conclusion after the visit

    // Constructor
    public Appointment() {

    }

    public Appointment(LocalDate date, LocalTime startTime, LocalTime endTime,
            Patient patient, Doctor doctor, String appointmentPurpose) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentPurpose = appointmentPurpose;
    }

    // Getters and Setters
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getAppointmentPurpose() {
        return appointmentPurpose;
    }

    public void setAppointmentPurpose(String appointmentPurpose) {
        this.appointmentPurpose = appointmentPurpose;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId='" + appointmentId + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", patient=" + (patient != null ? patient.getPatientId() : "null") +
                ", doctor=" + (doctor != null ? doctor.getDoctorId() : "null") +
                ", appointmentPurpose='" + appointmentPurpose + '\'' +
                ", observations='" + observations + '\'' +
                '}';

    }




}
