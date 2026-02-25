package com.hogwarts.hogwartsapi.EstudianteStuff;

import com.hogwarts.hogwartsapi.Asignatura.Asignatura;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estudiante_asignatura")
@Data
public class EstudianteAsignatura {

    @EmbeddedId
    EstudianteClaseCompuesta id;

    @ManyToOne
    @MapsId("id_estudiante")
    @JoinColumn(name = "id_estudiante", referencedColumnName = "id_estudiante")
    private Estudiante estudiante;


    @ManyToOne
    @MapsId("id_asignatura")
    @JoinColumn(name = "id_asignatura", referencedColumnName = "id_asignatura")
    private Asignatura asignatura;


    //de aqui pilla las calificaciones
    @Column(name = "calificacion")
    private Double calificacion;

    public EstudianteAsignatura() {
    }

    public EstudianteAsignatura(Estudiante estudiante, Asignatura asignatura, Double calificacion) {
        this.estudiante = estudiante;
        this.asignatura = asignatura;
        this.calificacion = calificacion;
        this.id = new EstudianteClaseCompuesta(estudiante.getId(), asignatura.getId());
    }
}
