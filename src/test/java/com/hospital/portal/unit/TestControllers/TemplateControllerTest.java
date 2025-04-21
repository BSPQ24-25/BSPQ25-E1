package com.hospital.portal.unit.TestControllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.hospital.portal.controller.TemplateController;
import com.hospital.portal.model.Patient;
import com.hospital.portal.service.PatientService;

@ExtendWith(MockitoExtension.class)
class TemplateControllerTest {

    @Mock
    private PatientService patientService;

    @Mock
    private Model model;

    @InjectMocks
    private TemplateController templateController;

    @Test
    void testShowIndex() {
        String viewName = templateController.showIndex();
        // Verificamos que la vista devuelta sea "index"
        assert viewName.equals("index");
    }

    @Test
    void testShowRegistrationForm() {
        String viewName = templateController.showRegistrationForm();
        // Verificamos que la vista devuelta sea "registrationForm"
        assert viewName.equals("registrationForm");
    }

    @Test
    void testShowLoginForm() {
        String viewName = templateController.showLoginForm();
        // Verificamos que la vista devuelta sea "loginForm"
        assert viewName.equals("loginForm");
    }

    @Test
    void testShowInicio() {
        String viewName = templateController.showInicio();
        // Verificamos que la vista devuelta sea "homepage"
        assert viewName.equals("homepage");
    }

    @Test
    void testShowInicioPaciente() {
        String dni = "12345678A";
        Patient patient = new Patient();
        patient.setDni(dni);
        patient.setName("John Doe");

        // Simulamos el comportamiento del servicio
        when(patientService.findPatientByDni(dni)).thenReturn(patient);

        String viewName = templateController.showInicioPaciente(dni, model);
        // Verificamos que la vista devuelta sea "patientDataView"
        assert viewName.equals("patientDataView");

        // Verificamos que el paciente se haya añadido al modelo
        verify(model).addAttribute("patient", patient);

        // Verificamos que el servicio fue llamado con el DNI correcto
        verify(patientService).findPatientByDni(dni);
    }

    @Test
    void testShowInicioDoctor() {
        String viewName = templateController.showInicioDoctor();
        // Verificamos que la vista devuelta sea "doctorHome"
        assert viewName.equals("doctorHome");
    }

    @Test
    void testShowInicioAdmin() {
        String viewName = templateController.showInicioAdmin();
        // Verificamos que la vista devuelta sea "adminHome"
        assert viewName.equals("adminHome");
    }
}
