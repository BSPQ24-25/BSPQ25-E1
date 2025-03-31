package com.hospital.portal.repository;

import com.hospital.portal.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, String> {
    boolean existsByDni(String dni);
    Optional<Patient> findTopByOrderByPatientIdDesc();
    boolean existsByMail(String mail);
}

