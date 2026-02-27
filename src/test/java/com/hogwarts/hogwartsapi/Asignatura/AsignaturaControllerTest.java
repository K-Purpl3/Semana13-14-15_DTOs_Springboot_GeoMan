package com.hogwarts.hogwartsapi.Asignatura;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AsignaturaController.class)
public class AsignaturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AsignaturaService asignaturaService;

    @Test
    public void deleteAsignatura_withStudents_shouldReturnConflict() throws Exception {
        // Given
        Long asignaturaId = 1L;
        doThrow(new DataIntegrityViolationException("Cannot delete asignatura with students"))
                .when(asignaturaService).deleteAsignatura(asignaturaId);

        // When & Then
        mockMvc.perform(delete("/asignaturas/" + asignaturaId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }
}
