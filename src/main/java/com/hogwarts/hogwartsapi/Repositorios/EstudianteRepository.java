package com.hogwarts.hogwartsapi.Repositorios;

import com.hogwarts.hogwartsapi.EstudianteStuff.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    List<Estudiante> findByCasaNombre(String nombreCasa);
    List<Estudiante> findByAnyoCurso(Integer anyoCurso);
}
