package com.hospital.portal.repository;

import com.hospital.portal.model.Doctor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, String> {
	Optional<Doctor> findByDoctorId(String doctorId);
}
