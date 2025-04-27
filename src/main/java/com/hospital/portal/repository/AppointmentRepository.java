package com.hospital.portal.repository;

import com.hospital.portal.model.Appointment;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
  List<Appointment> findByDoctorDni(String dni);

  List<Appointment> findByPatientDni(String patientDni);

  List<Appointment> findByPatientDniAndDate(String patientDni, LocalDate dateTime);

  List<Appointment> findByPatientDniAndDoctorSpecialtyName(String patientDni, String specialty_name);
}
