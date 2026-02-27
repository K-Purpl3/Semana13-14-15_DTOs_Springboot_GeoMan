package com.hogwarts.hogwartsapi.EstudianteStuff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hogwarts.hogwartsapi.DTO.EstudianteDTO.EstudianteCreateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EstudianteController.class)
public class EstudianteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private EstudianteService estudianteService;

    @Test
    public void createStudent_withInvalidYear_shouldReturnBadRequest() throws Exception {
        // Given
        EstudianteCreateDTO createDTO = new EstudianteCreateDTO();
        createDTO.setNombre("Draco");
        createDTO.setApellido("Malfoy");
        createDTO.setAnyoCurso(10);
        createDTO.setFechaNacimiento(LocalDate.of(1980, 6, 5));

        when(estudianteService.createStudent(any(EstudianteCreateDTO.class)))
                .thenThrow(new ConstraintViolationException("El año de curso no puede ser mayor a 6", null));

        // When & Then
        mockMvc.perform(post("/estudiantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isBadRequest());
    }
}
