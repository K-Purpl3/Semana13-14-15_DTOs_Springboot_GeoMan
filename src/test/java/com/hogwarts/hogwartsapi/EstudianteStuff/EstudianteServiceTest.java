package com.hogwarts.hogwartsapi.EstudianteStuff;

import com.hogwarts.hogwartsapi.Repositorios.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstudianteServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private EstudianteService estudianteService;

    private Estudiante harryPotter;

    @BeforeEach
    void setUp() {
        harryPotter = new Estudiante();
        harryPotter.setId(1L);
        harryPotter.setNombre("Harry");
        harryPotter.setApellido("Potter");
    }

    @Test
    void deleteById_ShouldDeleteStudent_WhenStudentExists() {
        //given
        Long studentId = 1L;
        when(studentRepository.existsById(studentId)).thenReturn(true);

        //when
        estudianteService.deleteById(studentId);

        //then
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void deleteById_ShouldThrowException_WhenStudentDoesNotExist() {
        //given
        Long studentId = 1L;
        when(studentRepository.existsById(studentId)).thenReturn(false);

        //when y then
        assertThrows(EntityNotFoundException.class, () -> estudianteService.deleteById(studentId));
        verify(studentRepository, never()).deleteById(studentId);
    }
}
