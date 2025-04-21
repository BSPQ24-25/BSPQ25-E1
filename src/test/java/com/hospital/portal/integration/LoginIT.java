package com.hospital.portal.integration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // Usamos un puerto aleatorio
class LoginIT {

    @Autowired
    private TestRestTemplate restTemplate;

  @Test
void testLogin() {
    // Step 1: Prepare valid login credentials
    String dni = "56789123P"; // Example DNI
    String password = "PasswordPatient0_"; // Example password

    // Step 2: Make the login request
    ResponseEntity<String> loginResponse = restTemplate.exchange(
        "/login?dni=" + dni + "&password=" + password, 
        HttpMethod.POST, 
        null, 
        String.class // Cambiado de Map.class a String.class
    );

    // Step 3: Verify the login response status
    assertEquals(HttpStatus.OK, loginResponse.getStatusCode());

    // Step 4: Parse response body as JSON (si esperas JSON)
    String body = loginResponse.getBody();
    assertNotNull(body);

    // Puedes usar una librería como Jackson para convertir el String a Map si es necesario
    ObjectMapper mapper = new ObjectMapper();
    try {
        Map<String, Object> responseBody = mapper.readValue(body, Map.class);
        assertTrue(responseBody.containsKey("role"));
        assertTrue(responseBody.containsKey("dni"));
        assertTrue(responseBody.containsKey("name"));
        assertTrue(responseBody.containsKey("token"));

        String role = (String) responseBody.get("role");
        assertTrue(role.equals("DOCTOR") || role.equals("PATIENT") || role.equals("ADMIN"));

        String token = (String) responseBody.get("token");
        assertNotNull(token);
    } catch (JsonProcessingException e) {
        fail("Response was not valid JSON: " + body);
    }
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

