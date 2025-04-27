
package com.hospital.portal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.portal.model.Doctor;
import com.hospital.portal.repository.DoctorRepository;

@Service
public class DoctorService {

  @Autowired
  private DoctorRepository doctorRepository;

  public Doctor findPatientByDni(String dni) {
    Optional<Doctor> patientOpt = doctorRepository.findByDni(dni);
    return patientOpt.orElse(null);
  }

}
