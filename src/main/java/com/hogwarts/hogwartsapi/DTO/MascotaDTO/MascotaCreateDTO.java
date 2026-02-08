package com.hogwarts.hogwartsapi.DTO.MascotaDTO;

import jakarta.validation.constraints.NotBlank;

public class MascotaCreateDTO {
    @NotBlank(message = "El nombre de la mascota es obligatorio")
    private String nombre;
    
    @NotBlank(message = "La especie de la mascota es obligatoria")
    private String especie;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
}
