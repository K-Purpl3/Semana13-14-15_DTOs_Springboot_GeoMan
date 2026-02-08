package com.hogwarts.hogwartsapi.Asignatura;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "asignatura")
@Data
public class Asignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignatura")
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "aula", nullable = false)
    private String aula;

    @Column(name = "obligatoria", nullable = false)
    private Boolean obligatoria;
}