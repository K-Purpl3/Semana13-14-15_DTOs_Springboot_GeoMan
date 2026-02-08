package com.hogwarts.hogwartsapi.MascotaProfesor;


import com.hogwarts.hogwartsapi.EstudianteStuff.Student;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mascota")
@Data
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "especie", nullable = false)
    private String especie;

    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Student estudiante;
}