package com.hospital.portal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.portal.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, String> {
    boolean existsByDni(String dni);
    Optional<Patient> findTopByOrderByPatientIdDesc();
    boolean existsByMail(String mail);
}

