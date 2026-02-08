package com.hogwarts.hogwartsapi.Repositorios;

import com.hogwarts.hogwartsapi.MascotaProfesor.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}
