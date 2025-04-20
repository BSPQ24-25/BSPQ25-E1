package com.hospital.portal.repository;

import com.hospital.portal.model.Appointment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
	List<Appointment> findByDoctorDoctorId(String doctorId);
}