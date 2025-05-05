
package com.hospital.portal.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.portal.model.Doctor;
import com.hospital.portal.repository.DoctorRepository;


@Service
public class DoctorService {

  private static final Logger logger = LogManager.getLogger(DoctorService.class);


  @Autowired
  private DoctorRepository doctorRepository;

  public Doctor findPatientByDni(String dni) {
    logger.info("Finding Patient by DNI {}", dni);

    Optional<Doctor> patientOpt = doctorRepository.findByDni(dni);
    return patientOpt.orElse(null);
  }

}
