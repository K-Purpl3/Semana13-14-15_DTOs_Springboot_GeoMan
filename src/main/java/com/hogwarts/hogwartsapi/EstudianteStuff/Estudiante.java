package com.hogwarts.hogwartsapi.EstudianteStuff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hogwarts.hogwartsapi.Casa.Casa;
import com.hogwarts.hogwartsapi.MascotaProfesor.Mascota;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "estudiante")
@Data
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @ManyToOne
    @JoinColumn(name = "id_casa", referencedColumnName = "id_casa")
    private Casa casa;

    @Column(name = "anyo_curso", nullable = false)
    private Integer anyoCurso;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @OneToMany(mappedBy = "estudiante")
    @JsonManagedReference("estudiante-mascotas")
    private List<Mascota> mascotas;

    // Relación inversa con EstudianteAsignatura
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    @JsonIgnore // Para evitar bucles infinitos en JSON
    private List<EstudianteAsignatura> asignaturasCursadas = new ArrayList<>();
}
