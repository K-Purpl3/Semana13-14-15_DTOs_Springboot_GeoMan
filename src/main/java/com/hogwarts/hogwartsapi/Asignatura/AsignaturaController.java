package com.hogwarts.hogwartsapi.Asignatura;

import com.hogwarts.hogwartsapi.DTO.AsignaturaDTO.AsignaturaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturaController {

    private final AsignaturaService asignaturaService;

    @Autowired
    public AsignaturaController(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    @Operation(summary = "Obtiene todas las asignaturas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asignaturas encontradas")
    })
    @GetMapping
    public ResponseEntity<List<AsignaturaDTO>> getAllAsignaturas() {
        return ResponseEntity.ok(asignaturaService.findAll());
    }

    @Operation(summary = "Obtiene una asignatura por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asignatura encontrada"),
            @ApiResponse(responseCode = "404", description = "Asignatura no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AsignaturaDTO> getAsignaturaById(@PathVariable Long id) {
        AsignaturaDTO asignatura = asignaturaService.findById(id);
        return asignatura != null ? ResponseEntity.ok(asignatura) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Elimina una asignatura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Asignatura eliminada"),
            @ApiResponse(responseCode = "409", description = "No se puede eliminar la asignatura porque tiene estudiantes inscritos")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsignatura(@PathVariable Long id) {
        try {
            asignaturaService.deleteAsignatura(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
