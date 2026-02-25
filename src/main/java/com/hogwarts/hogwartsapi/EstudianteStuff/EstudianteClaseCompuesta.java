package com.hogwarts.hogwartsapi.EstudianteStuff;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
class EstudianteClaseCompuesta implements Serializable {

    @Column(name = "id_estudiante")
    Long id_estudiante;

    @Column(name = "id_asignatura")
    Long id_asignatura;

    public EstudianteClaseCompuesta() {
    }

    public EstudianteClaseCompuesta(Long id_estudiante, Long id_asignatura) {
        this.id_estudiante = id_estudiante;
        this.id_asignatura = id_asignatura;
    }

    public Long getId_estudiante() {
        return id_estudiante;
    }

    public void setId_estudiante(Long id_estudiante) {
        this.id_estudiante = id_estudiante;
    }

    public Long getId_asignatura() {
        return id_asignatura;
    }

    public void setId_asignatura(Long id_asignatura) {
        this.id_asignatura = id_asignatura;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstudianteClaseCompuesta that = (EstudianteClaseCompuesta) o;
        return Objects.equals(id_estudiante, that.id_estudiante) &&
                Objects.equals(id_asignatura, that.id_asignatura);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_estudiante, id_asignatura);
    }
}
