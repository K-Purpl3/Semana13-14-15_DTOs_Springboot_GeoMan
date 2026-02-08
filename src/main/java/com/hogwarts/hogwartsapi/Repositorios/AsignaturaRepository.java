package com.hogwarts.hogwartsapi.Repositorios;

import com.hogwarts.hogwartsapi.Asignatura.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    Optional<Asignatura> findByNombre(String nombre);
    List<Asignatura> findByObligatoria(Boolean obligatoria);
}