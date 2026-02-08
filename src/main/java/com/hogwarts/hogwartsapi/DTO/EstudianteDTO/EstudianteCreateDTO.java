package com.hogwarts.hogwartsapi.DTO.EstudianteDTO;

import com.hogwarts.hogwartsapi.DTO.MascotaDTO.MascotaCreateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class EstudianteCreateDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotNull(message = "El año de curso es obligatorio")
    @Min(value = 1, message = "El año de curso debe ser mayor a 0")
    private Integer anyoCurso;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El ID de la casa es obligatorio")
    private Long casaId;

    @Valid
    private MascotaCreateDTO mascota;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getAnyoCurso() {
        return anyoCurso;
    }

    public void setAnyoCurso(Integer anyoCurso) {
        this.anyoCurso = anyoCurso;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getCasaId() {
        return casaId;
    }

    public void setCasaId(Long casaId) {
        this.casaId = casaId;
    }

    public MascotaCreateDTO getMascota() {
        return mascota;
    }

    public void setMascota(MascotaCreateDTO mascota) {
        this.mascota = mascota;
    }
}
