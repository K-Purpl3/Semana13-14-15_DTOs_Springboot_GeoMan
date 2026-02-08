package com.hogwarts.hogwartsapi.Repositorios;

import com.hogwarts.hogwartsapi.EstudianteStuff.EstudianteAsignatura;
import com.hogwarts.hogwartsapi.EstudianteStuff.EstudianteAsignaturaID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstudianteAsignaturaRepository extends
        JpaRepository<EstudianteAsignatura, EstudianteAsignaturaID> {

    //buscar por ID de estudiante
    List<EstudianteAsignatura> findByEstudianteId(Long estudianteId);

    //buscar por ID de asignatura
    List<EstudianteAsignatura> findByAsignaturaId(Long asignaturaId);

    //buscar por estudiante y asignatura
    List<EstudianteAsignatura> findByEstudianteIdAndAsignaturaId(Long estudianteId, Long asignaturaId);

    //obtener promedio de notas de un estudiante
    Double findAvgCalificacionByEstudianteId(Long estudianteId);

    //obtener promedio de calificaciones de una asignatura
    Double findAvgCalificacionByAsignaturaId(Long asignaturaId);

    //contar estudiantes en una asignatura
    Long countByAsignaturaId(Long asignaturaId);
}