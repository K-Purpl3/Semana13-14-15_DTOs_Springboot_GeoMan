package com.hogwarts.hogwartsapi.EstudianteStuff;

import com.hogwarts.hogwartsapi.Asignatura.Asignatura;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estudiante_asignatura")
@Data
@IdClass(EstudianteAsignaturaID.class)
public class EstudianteAsignatura {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_estudiante", referencedColumnName = "id_estudiante")
    private Student estudiante;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_asignatura", referencedColumnName = "id_asignatura")
    private Asignatura asignatura;

    @Column(name = "calificacion")
    private Double calificacion;

    public EstudianteAsignatura() {
    }

    public EstudianteAsignatura(Student estudiante, Asignatura asignatura, Double calificacion) {
        this.estudiante = estudiante;
        this.asignatura = asignatura;
        this.calificacion = calificacion;
    }
}