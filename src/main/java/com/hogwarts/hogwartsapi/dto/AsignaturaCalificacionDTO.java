package com.hogwarts.hogwartsapi.dto;

public class AsignaturaCalificacionDTO {
    private String asignatura;
    private Double calificacion;

    // Getters and Setters
    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }
}
