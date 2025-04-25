package com.hospital.portal.service;

import com.hospital.portal.model.Appointment;
import com.hospital.portal.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AdminService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
