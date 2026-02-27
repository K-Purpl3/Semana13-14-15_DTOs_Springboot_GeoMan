package com.hogwarts.hogwartsapi.Repositorios;

import com.hogwarts.hogwartsapi.EstudianteStuff.Estudiante;
import com.hogwarts.hogwartsapi.MascotaProfesor.Mascota;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Test
    public void whenDeleteStudent_thenMascotaShouldBeDeleted() {
        //given
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Harry");
        estudiante.setApellido("Potter");
        estudiante.setAnyoCurso(1);
        estudiante.setFechaNacimiento(LocalDate.of(1980, 7, 31));

        Mascota mascota = new Mascota();
        mascota.setNombre("Hedwig");
        mascota.setEspecie("Lechuza");
        mascota.setEstudiante(estudiante);

        List<Mascota> mascotas = new ArrayList<>();
        mascotas.add(mascota);
        estudiante.setMascotas(mascotas);

        //persist marca los objetos paa ser guardados
        //flush ejecuta el guardado para que los datos existan en la bbdd
        Estudiante savedEstudiante = entityManager.persist(estudiante);
        entityManager.flush();

        Long estudianteId = savedEstudiante.getId();
        Long mascotaId = savedEstudiante.getMascotas().get(0).getId();

        //verificar que estudiante y mascota existen antes de borrarlos
        assertThat(studentRepository.findById(estudianteId)).isPresent();
        assertThat(mascotaRepository.findById(mascotaId)).isPresent();

        //when
        studentRepository.deleteById(estudianteId);
        entityManager.flush(); //sincronizar con la base de datos

        //then
        assertThat(studentRepository.findById(estudianteId)).isEmpty();
        assertThat(mascotaRepository.findById(mascotaId)).isEmpty();
    }
}
