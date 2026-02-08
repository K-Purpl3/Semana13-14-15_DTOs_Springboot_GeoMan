package com.hogwarts.hogwartsapi.Repositorios;

import com.hogwarts.hogwartsapi.Casa.Casa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CasaRepository extends JpaRepository<Casa, Long> {
    Optional<Casa> findByNombre(String nombre);
}