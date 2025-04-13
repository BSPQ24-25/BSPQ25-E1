package com.hospital.portal.integration;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // Usamos un puerto aleatorio
class LoginIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testLogin() {
        // Step 1: Prepare valid login credentials
        String dni = "11111111X"; // Example DNI
        String password = "Iker@2024!"; // Example password

        // Step 2: Make the login request
        ResponseEntity<Map> loginResponse = restTemplate.exchange(
            "/login?dni=" + dni + "&password=" + password, 
            HttpMethod.POST, 
            null, 
            Map.class
        );

        // Step 3: Verify the login response
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        Map<String, Object> responseBody = loginResponse.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("role"));
        assertTrue(responseBody.containsKey("dni"));
        assertTrue(responseBody.containsKey("name"));
        assertTrue(responseBody.containsKey("token"));

        // Step 4: Verify the role and redirect path based on the role
        String role = (String) responseBody.get("role");
        assertTrue(role.equals("DOCTOR") || role.equals("PATIENT") || role.equals("ADMIN"));
        
        // Optionally, you can check if the token is correctly formed, but this can depend on your implementation.
        String token = (String) responseBody.get("token");
        assertNotNull(token);
    }

    @Test
    void testLoginInvalidCredentials() {
        // Step 1: Prepare invalid login credentials
        String dni = "invalidDNI"; // Invalid DNI
        String password = "invalidPassword"; // Invalid password

        // Step 2: Make the login request
        ResponseEntity<String> loginResponse = restTemplate.exchange(
            "/login?dni=" + dni + "&password=" + password, 
            HttpMethod.POST, 
            null, 
            String.class
        );

        // Step 3: Verify the response for invalid credentials
        assertEquals(HttpStatus.UNAUTHORIZED, loginResponse.getStatusCode());
        assertEquals("Invalid credentials", loginResponse.getBody());
    }


    
}

// comando para ejecutar  -- mvn clean verify -Pintegration

