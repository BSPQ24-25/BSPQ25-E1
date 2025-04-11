package com.hospital.portal.unit.TestModels;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hospital.portal.model.Doctor;
import com.hospital.portal.model.Specialty;
import com.hospital.portal.repository.DoctorRepository;

class DoctorTest {

    private DoctorRepository repository;
    private Doctor doctor;
    private Specialty specialty;

    @BeforeEach
    void setUp() {
        // Create the Specialty object
        specialty = new Specialty("Cardiology" ," heart");

        // Create the Doctor object with the specialty
        doctor = new Doctor("1", "Dr. James", "Smith", LocalDate.of(1980, 7, 20), "Male", "123456789", "james.smith@mail.com", "password789", "doctor001", specialty);

        // Mock the DoctorRepository
        repository = mock(DoctorRepository.class);
    }

    @Test
    @DisplayName("Should Add Doctor Successfully")
    void testAddDoctor() {
        // Simulate that the save method returns the doctor object
        when(repository.save(doctor)).thenReturn(doctor);

        // Call the save method
        Doctor result = repository.save(doctor);

        // Verify that save was called once
        verify(repository, times(1)).save(doctor);

        // Check that the saved doctor has the correct doctorId
        assertEquals("doctor001", result.getDoctorId());

        // Check that the saved doctor has the correct specialty
        assertNotNull(result.getSpecialty());
        assertEquals("Cardiology", result.getSpecialty().getName());
    }

    @Test
    @DisplayName("Should Retrieve Doctor By ID")
    void testGetDoctorById() {
        // Simulate finding the doctor with ID "1"
        when(repository.findById("1")).thenReturn(Optional.of(doctor));

        // Get the doctor from the repository
        Doctor result = repository.findById("1").orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Verify that the retrieved doctor is not null and has the correct name
        assertNotNull(result);
        assertEquals("Dr. James", result.getName());
    }

    @Test
    @DisplayName("Should Update Doctor Doctor ID")
    void testUpdateDoctorDoctorId() {
        // Change the doctorId of the Doctor
        doctor.setDoctorId("doctor002");

        // Simulate that the save method updates the doctor correctly
        when(repository.save(doctor)).thenReturn(doctor);

        // Update the doctor
        Doctor result = repository.save(doctor);

        // Verify that the save method was called with the updated doctor
        verify(repository, times(1)).save(doctor);

        // Check that the doctorId was updated correctly
        assertEquals("doctor002", result.getDoctorId());
    }

    @Test
    @DisplayName("Should Throw Exception When Doctor Not Found")
    void testGetDoctorById_NotFound() {
        // Simulate that the doctor with ID "999" does not exist
        when(repository.findById("999")).thenReturn(Optional.empty());

        // Try to get a doctor that doesn't exist
        Exception exception = assertThrows(RuntimeException.class, () -> {
            repository.findById("999").orElseThrow(() -> new RuntimeException("Doctor not found"));
        });

        // Verify that the exception thrown is as expected
        assertEquals("Doctor not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should Get and Set Specialty Correctly")
    void testGetSetSpecialty() {
        Specialty newSpecialty = new Specialty("Neurology","neurology");
        doctor.setSpecialty(newSpecialty);
        assertEquals("Neurology", doctor.getSpecialty().getName());
    }

    @Test
    @DisplayName("Should Get and Set Doctor ID Correctly")
    void testGetSetDoctorId() {
        doctor.setDoctorId("doctor003");
        assertEquals("doctor003", doctor.getDoctorId());
    }
}
