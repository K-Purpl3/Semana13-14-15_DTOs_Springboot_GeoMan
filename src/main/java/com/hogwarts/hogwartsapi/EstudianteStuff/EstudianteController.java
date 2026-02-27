package com.hogwarts.hogwartsapi.EstudianteStuff;

import com.hogwarts.hogwartsapi.DTO.EstudianteDTO.EstudianteCreateDTO;
import com.hogwarts.hogwartsapi.DTO.EstudianteDTO.EstudianteDTO;
import com.hogwarts.hogwartsapi.DTO.EstudianteDTO.EstudianteListaDTO;
import com.hogwarts.hogwartsapi.DTO.EstudianteDTO.EstudianteUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.hibernate.annotations.SoftDelete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {
    private final EstudianteService estudianteService;

    @Autowired
    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @Operation(summary = "Obtiene todos los estudiantes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiantes encontrados")
    })
    @GetMapping
    public ResponseEntity<List<EstudianteListaDTO>> getAllStudents() {
        return ResponseEntity.ok(estudianteService.findAll());
    }

    @Operation(summary = "Obtiene un estudiante por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante encontrado"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> getStudentById(@PathVariable Long id) {
        EstudianteDTO student = estudianteService.findById(id);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crea un nuevo estudiante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudiante creado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<EstudianteDTO> createStudent(@Valid @RequestBody EstudianteCreateDTO createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estudianteService.createStudent(createDTO));
    }

    @Operation(summary = "Actualiza un estudiante existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante actualizado"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody EstudianteUpdateDTO updateDTO) {
        return ResponseEntity.ok(estudianteService.updateStudent(id, updateDTO));
    }

    @Operation(summary = "Elimina un estudiante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estudiante eliminado"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        estudianteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
