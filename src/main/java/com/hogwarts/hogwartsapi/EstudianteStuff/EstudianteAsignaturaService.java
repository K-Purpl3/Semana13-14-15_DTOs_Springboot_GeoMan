package com.hogwarts.hogwartsapi.EstudianteStuff;

import com.hogwarts.hogwartsapi.Asignatura.Asignatura;
import com.hogwarts.hogwartsapi.Repositorios.AsignaturaRepository;
import com.hogwarts.hogwartsapi.Repositorios.EstudianteAsignaturaRepository;
import com.hogwarts.hogwartsapi.Repositorios.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteAsignaturaService {

    @Autowired
    private EstudianteAsignaturaRepository estudianteAsignaturaRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    // Guardar o actualizar una relación estudiante-asignatura
    public EstudianteAsignatura saveOrUpdate(Long estudianteId, Long asignaturaId, Double calificacion) {
        Optional<Student> estudiante = studentRepository.findById(estudianteId);
        Optional<Asignatura> asignatura = asignaturaRepository.findById(asignaturaId);

        if (estudiante.isPresent() && asignatura.isPresent()) {
            EstudianteAsignatura relacion = new EstudianteAsignatura();
            relacion.setEstudiante(estudiante.get());
            relacion.setAsignatura(asignatura.get());
            relacion.setCalificacion(calificacion);

            return estudianteAsignaturaRepository.save(relacion);
        }
        return null;
    }

    // Obtener todas las relaciones
    public List<EstudianteAsignatura> findAll() {
        return estudianteAsignaturaRepository.findAll();
    }

    // Obtener relaciones por estudiante
    public List<EstudianteAsignatura> findByEstudianteId(Long estudianteId) {
        return estudianteAsignaturaRepository.findByEstudianteId(estudianteId);
    }

    // Obtener relaciones por asignatura
    public List<EstudianteAsignatura> findByAsignaturaId(Long asignaturaId) {
        return estudianteAsignaturaRepository.findByAsignaturaId(asignaturaId);
    }

    // Eliminar una relación
    public void delete(Long estudianteId, Long asignaturaId) {
        List<EstudianteAsignatura> relaciones = estudianteAsignaturaRepository
                .findByEstudianteIdAndAsignaturaId(estudianteId, asignaturaId);

        if (!relaciones.isEmpty()) {
            estudianteAsignaturaRepository.delete(relaciones.get(0));
        }
    }

    // Calcular promedio de un estudiante
    public Double calcularPromedioEstudiante(Long estudianteId) {
        return estudianteAsignaturaRepository.findAvgCalificacionByEstudianteId(estudianteId);
    }
}