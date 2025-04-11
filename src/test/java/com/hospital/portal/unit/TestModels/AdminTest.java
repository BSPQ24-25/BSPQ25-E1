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

import com.hospital.portal.model.Admin;
import com.hospital.portal.repository.AdminRepository;

class AdminTest {

    private AdminRepository repository;
    private Admin admin;

    @BeforeEach
    void setUp() {
        // Create the Admin object
        admin = new Admin("1", "Alice", "Smith", LocalDate.of(1985, 5, 10), "Female", "123456789", "alice@mail.com", "password123", "worker001");

        // Mock the AdminRepository
        repository = mock(AdminRepository.class);
    }

    @Test
    @DisplayName("Should Add Admin Successfully")
    void testAddAdmin() {
        // Simulate that the save method returns the admin object
        when(repository.save(admin)).thenReturn(admin);

        // Call the save method
        Admin result = repository.save(admin);

        // Verify that save was called once
        verify(repository, times(1)).save(admin);

        // Check that the saved admin has the correct workerId
        assertEquals("worker001", result.getWorkerId());
    }

    @Test
    @DisplayName("Should Retrieve Admin By ID")
    void testGetAdminById() {
        // Simulate finding the admin with ID "1"
        when(repository.findById("1")).thenReturn(Optional.of(admin));

        // Get the admin from the repository
        Admin result = repository.findById("1").orElseThrow(() -> new RuntimeException("Admin not found"));

        // Verify that the retrieved admin is not null and has the correct name
        assertNotNull(result);
        assertEquals("Alice", result.getName());
    }

    @Test
    @DisplayName("Should Update Admin Worker ID")
    void testUpdateAdminWorkerId() {
        // Change the workerId of the Admin
        admin.setWorkerId("worker002");

        // Simulate that the save method updates the admin correctly
        when(repository.save(admin)).thenReturn(admin);

        // Update the admin
        Admin result = repository.save(admin);

        // Verify that the save method was called with the updated admin
        verify(repository, times(1)).save(admin);

        // Check that the workerId was updated correctly
        assertEquals("worker002", result.getWorkerId());
    }

    @Test
    @DisplayName("Should Throw Exception When Admin Not Found")
    void testGetAdminById_NotFound() {
        // Simulate that the admin with ID "999" does not exist
        when(repository.findById("999")).thenReturn(Optional.empty());

        // Try to get an admin that doesn't exist
        Exception exception = assertThrows(RuntimeException.class, () -> {
            repository.findById("999").orElseThrow(() -> new RuntimeException("Admin not found"));
        });

        // Verify that the exception thrown is as expected
        assertEquals("Admin not found", exception.getMessage());
    }
}
