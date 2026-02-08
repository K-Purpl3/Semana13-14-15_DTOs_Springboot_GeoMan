package com.hogwarts.hogwartsapi.EstudianteStuff;

import java.io.Serializable;
import java.util.Objects;

public class EstudianteAsignaturaID implements Serializable {
    private Long estudiante;
    private Long asignatura;

    public EstudianteAsignaturaID() {
    }
    public EstudianteAsignaturaID(Long estudiante, Long asignatura) {
        this.estudiante = estudiante;
        this.asignatura = asignatura;
    }

    public Long getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Long estudiante) {
        this.estudiante = estudiante;
    }

    public Long getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Long asignatura) {
        this.asignatura = asignatura;
    }

    //equals() necesario para comparar instancias
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EstudianteAsignaturaID that = (EstudianteAsignaturaID) o;

        return Objects.equals(estudiante, that.estudiante) &&
                Objects.equals(asignatura, that.asignatura);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estudiante, asignatura);
    }

    @Override
    public String toString() {
        return "EstudianteAsignaturaID{" +
                "estudiante=" + estudiante +
                ", asignatura=" + asignatura +
                '}';
    }
}