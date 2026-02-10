package com.hogwarts.hogwartsapi.EstudianteStuff;

import com.hogwarts.hogwartsapi.DTO.EstudianteDTO.EstudianteCreateDTO;
import com.hogwarts.hogwartsapi.DTO.EstudianteDTO.EstudianteDTO;
import com.hogwarts.hogwartsapi.DTO.EstudianteDTO.EstudianteUpdateDTO;
import jakarta.validation.Valid;
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

    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> getAllStudents() {
        return ResponseEntity.ok(estudianteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> getStudentById(@PathVariable Long id) {
        EstudianteDTO student = estudianteService.findById(id);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EstudianteDTO> createStudent(@Valid @RequestBody EstudianteCreateDTO createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estudianteService.createStudent(createDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody EstudianteUpdateDTO updateDTO) {
        return ResponseEntity.ok(estudianteService.updateStudent(id, updateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        estudianteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }  //saca codigo 204 no content
}
