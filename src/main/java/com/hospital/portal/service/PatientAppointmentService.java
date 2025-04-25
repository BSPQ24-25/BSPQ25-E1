package com.hospital.portal.service;

import com.hospital.portal.model.Patient;
import com.hospital.portal.model.Appointment;
import com.hospital.portal.repository.AppointmentRepository;
import com.hospital.portal.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientAppointmentService {
    
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;  

    public PatientAppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository){
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository; 
    }

    public List<Appointment> getAppointmentsByPatientDNI(String patientDNI) {
        return appointmentRepository.findByPatientDni(patientDNI);
    }

    public List<Appointment> getAppointmentByPatientDniAndDoctorSpecialtyName(String patientDni, String specialty_name) {
        return appointmentRepository.findByPatientDniAndDoctorSpecialtyName(patientDni, specialty_name);
    }

    public List<Appointment> getAppointmentsByDate(String patientDNI, LocalDate dateTime) {
        return appointmentRepository.findByPatientDniAndDate(patientDNI, dateTime);
    }
    
    public Appointment addAppointmentPatient(String pateintDNI, Appointment appointment) {
        Patient patient = patientRepository.findByDni(pateintDNI).orElseThrow(() -> new IllegalArgumentException("Patient not found with DNI: " + pateintDNI));

        Appointment newAppointment = new Appointment();
        newAppointment.setDate(appointment.getDate());
        newAppointment.setStartTime(appointment.getStartTime());
        newAppointment.setEndTime(appointment.getEndTime());
        newAppointment.setDoctor(appointment.getDoctor());
        newAppointment.setPatient(patient);
        newAppointment.setAppointmentPurpose(appointment.getAppointmentPurpose());
        newAppointment.setObservations(appointment.getObservations());
        newAppointment.setAppointmentId(generateAppointmentId());

        return appointmentRepository.save(newAppointment);

    }

    private String generateAppointmentId() {
        return "APP" + System.currentTimeMillis(); 
    }

    public Appointment modifyAppointment(String id, Appointment modifiedAppointment){
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + id));
        
        if (modifiedAppointment.getDate() != null) {
            appointment.setDate(modifiedAppointment.getDate());
        }
        
        if (modifiedAppointment.getStartTime() != null) {
            appointment.setStartTime(modifiedAppointment.getStartTime());
        }
        
        if (modifiedAppointment.getEndTime() != null) {
            appointment.setEndTime(modifiedAppointment.getEndTime());
        }
        
        if (modifiedAppointment.getDoctor() != null) {
            appointment.setDoctor(modifiedAppointment.getDoctor());
        }
        
        if (modifiedAppointment.getAppointmentPurpose() != null) {
            appointment.setAppointmentPurpose(modifiedAppointment.getAppointmentPurpose());
        }

        if (modifiedAppointment.getObservations() != null) {
            appointment.setObservations(modifiedAppointment.getObservations());
        }

        return appointmentRepository.save(appointment);

    }
    
    public void deleteAppointmentPatient(String id) {
        appointmentRepository.deleteById(id);
    }

    
}

