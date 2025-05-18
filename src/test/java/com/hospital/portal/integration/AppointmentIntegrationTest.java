package com.hospital.portal.integration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hospital.portal.model.Appointment;
import com.hospital.portal.model.Patient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppointmentIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private ObjectMapper mapper;


    private static String token;
    private static String appointmentId;
    private static final String doctorDni = "135792468A";
    private static final String doctorPass = "PasswordDoc0_";

    @BeforeEach
    void setup() {
        // Configure this to serialize Date
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    @Order(1)
    void testCreateAppointment() throws Exception {
        System.out.println("Iniciando testCreateAppointmentWithExistingPatient...");

        // Login as doctor
        ResponseEntity<String> loginResponse = restTemplate.exchange(
                "/login?dni=" + doctorDni + "&password=" + doctorPass,
                HttpMethod.POST,
                null,
                String.class
        );

        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        Map<String, Object> loginBody = mapper.readValue(loginResponse.getBody(), Map.class);
        token = (String) loginBody.get("token");
        assertNotNull(token);

        // Use patient
        Patient patient = new Patient();
        patient.setDni("56789123P");

        // Create appointment
        Appointment appointment = new Appointment();
        appointment.setDate(LocalDate.of(2025, 7, 10));
        appointment.setStartTime(LocalTime.of(10, 0));
        appointment.setEndTime(LocalTime.of(10, 30));
        appointment.setPatient(patient);
        appointment.setAppointmentPurpose("Consulta general");
        appointment.setObservations("Observaciones desde el test");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(appointment), headers);

        ResponseEntity<Appointment> response = restTemplate.exchange(
                "/api/doctors/" + doctorDni + "/appointments",
                HttpMethod.POST,
                request,
                Appointment.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Appointment created = response.getBody();
        assertNotNull(created);
        assertEquals(LocalDate.of(2025, 7, 10), created.getDate());

        appointmentId = created.getAppointmentId();
        assertNotNull(appointmentId);
        System.out.println("Cita creada correctamente con ID: " + appointmentId);
    }

    @Test
    @Order(2)
    void testDeleteAppointment() {
        System.out.println("Iniciando testDeleteAppointment...");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);


        // delete appointment 
        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/doctors/appointments/" + appointmentId,
                HttpMethod.DELETE,
                request,
                Void.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("Cita eliminada correctamente con ID: " + appointmentId);
    }
}


// mvn clean verify -Pintegration
