package com.hospital.portal.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RegisterIT {

    @Autowired
    private TestRestTemplate restTemplate;

    // Usamos @Transactional para que los cambios en la BD se reviertan al final del test
    // @Test
    // @Transactional
    // void testRegisterSuccess() {
    //     String dni = "12388178B";
    //     String name = "John";
    //     String surname = "Doe";
    //     String phone = "123456789";
    //     String email = "ppe.doe@example.com";
    //     String birthDate = "1990-01-01"; 
    //     String gender = "Male";
    //     String password = "password123";
    
    //     ResponseEntity<String> registerResponse = restTemplate.exchange(
    //         "/register?dni=" + dni 
    //         + "&name=" + name 
    //         + "&surname=" + surname 
    //         + "&phone=" + phone 
    //         + "&email=" + email 
    //         + "&birthDate=" + birthDate 
    //         + "&gender=" + gender 
    //         + "&password=" + password,
    //         HttpMethod.POST,
    //         null, 
    //         String.class
    //     );
    
    //     // Verificamos que la respuesta sea exitosa
    //     assertEquals(HttpStatus.OK, registerResponse.getStatusCode());
    //     String responseBody = registerResponse.getBody();
    //     assertNotNull(responseBody);
    //     assertEquals("Registration successful", responseBody);

    //     // En este caso, después de que el test termine, la transacción se revertirá
    //     // y el paciente no quedará almacenado en la base de datos.
    // }
    @Test
    void testRegisterDniAlreadyExists() {
        // Step 1: Prepare valid registration data
        String dni = "12388178B";
        String name = "John";
        String surname = "Doe";
        String phone = "123456789";
        String email = "ppe.doe@example.com";
        String birthDate = "1990-01-01";
        String gender = "Male";
        String password = "password123";

        // Step 2: Try to register another patient with the same DNI
        ResponseEntity<String> registerResponse = restTemplate.exchange(
            "/register?dni=" + dni 
            + "&name=" + name 
            + "&surname=" + surname 
            + "&phone=" + phone 
            + "&email=" + email 
            + "&birthDate=" + birthDate 
            + "&gender=" + gender 
            + "&password=" + password,
            HttpMethod.POST,
            null, 
            String.class
        );

        // Step 3: Verify that the response indicates that the DNI already exists
        assertEquals(HttpStatus.CONFLICT, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidData() {
        // Step 1: Prepare invalid registration data (missing fields)
        String dni = "";  // DNI is empty
        String name = "John";
        String surname = "Doe";
        String phone = "123456789";
        String email = "";
        String birthDate = "1990-01-01";
        String gender = "Male";
        String password = "password123";

        // Step 2: Make the registration request
        ResponseEntity<String> registerResponse = restTemplate.exchange(
            "/register?dni=" + dni 
            + "&name=" + name 
            + "&surname=" + surname 
            + "&phone=" + phone 
            + "&email=" + email 
            + "&birthDate=" + birthDate 
            + "&gender=" + gender 
            + "&password=" + password,
            HttpMethod.POST,
            null, 
            String.class
        );

        // Step 3: Verify that the response indicates invalid data
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }
}
