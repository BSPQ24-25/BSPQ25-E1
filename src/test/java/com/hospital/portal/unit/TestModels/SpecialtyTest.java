package com.hospital.portal.unit.TestModels;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hospital.portal.model.Specialty;

class SpecialtyTest {

    private Specialty specialty;

    @BeforeEach
    void setUp() {
        // Create the Specialty object
        specialty = new Specialty("Cardiology", "Heart and vascular system diseases");
    }

    @Test
    @DisplayName("Should Get and Set Specialty Name Correctly")
    void testGetSetSpecialtyName() {
        // Test the getter and setter for name
        specialty.setName("Neurology");
        assertEquals("Neurology", specialty.getName());
    }

    @Test
    @DisplayName("Should Get and Set Specialty Description Correctly")
    void testGetSetSpecialtyDescription() {
        // Test the getter and setter for description
        specialty.setDescription("Neurological diseases and disorders");
        assertEquals("Neurological diseases and disorders", specialty.getDescription());
    }

    @Test
    @DisplayName("Should Get Specialty Name Correctly")
    void testGetSpecialtyName() {
        // Verify the name is correct after initialization
        assertEquals("Cardiology", specialty.getName());
    }

    @Test
    @DisplayName("Should Get Specialty Description Correctly")
    void testGetSpecialtyDescription() {
        // Verify the description is correct after initialization
        assertEquals("Heart and vascular system diseases", specialty.getDescription());
    }
}
