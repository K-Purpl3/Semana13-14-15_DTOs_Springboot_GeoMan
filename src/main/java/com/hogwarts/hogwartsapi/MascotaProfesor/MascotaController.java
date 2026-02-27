package com.hogwarts.hogwartsapi.MascotaProfesor;

import com.hogwarts.hogwartsapi.DTO.MascotaDTO.MascotaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    @Autowired
    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @Operation(summary = "Obtiene todas las mascotas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascotas encontradas")
    })
    @GetMapping
    public ResponseEntity<List<MascotaDTO>> getAllMascotas() {
        return ResponseEntity.ok(mascotaService.findAll());
    }

    @Operation(summary = "Obtiene una mascota por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascota encontrada"),
            @ApiResponse(responseCode = "404", description = "Mascota no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MascotaDTO> getMascotaById(@PathVariable Long id) {
        MascotaDTO mascota = mascotaService.findById(id);
        return mascota != null ? ResponseEntity.ok(mascota) : ResponseEntity.notFound().build();
    }
}
