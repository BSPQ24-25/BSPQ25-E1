package com.hospital.portal.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.portal.model.Appointment;
import com.hospital.portal.repository.AppointmentRepository;


@Service
public class AdminService {

    private final AppointmentRepository appointmentRepository;
    private static final Logger logger = LogManager.getLogger(AdminService.class);


    @Autowired
    public AdminService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAllAppointments() {
        logger.info("Returning all Appointments");
        return appointmentRepository.findAll();
    }
}
