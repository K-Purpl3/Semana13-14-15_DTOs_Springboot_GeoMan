package com.hogwarts.hogwartsapi.DTO.EstudianteDTO;

import com.hogwarts.hogwartsapi.DTO.MascotaDTO.MascotaCreateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class EstudianteUpdateDTO {
    @NotNull(message = "El año de curso es obligatorio")
    @Min(value = 1, message = "El año de curso debe ser mayor a 0")
    private Integer anyoCurso;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @Valid
    private MascotaCreateDTO mascota;

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

    public MascotaCreateDTO getMascota() {
        return mascota;
    }

    public void setMascota(MascotaCreateDTO mascota) {
        this.mascota = mascota;
    }
}
