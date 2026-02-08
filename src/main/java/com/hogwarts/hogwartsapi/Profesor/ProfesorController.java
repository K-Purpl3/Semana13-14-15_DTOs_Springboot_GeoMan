package com.hogwarts.hogwartsapi.Profesor;

import com.hogwarts.hogwartsapi.dto.ProfesorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {

    private final ProfesorService profesorService;

    @Autowired
    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    @GetMapping
    public ResponseEntity<List<ProfesorDTO>> getAllProfesores() {
        return ResponseEntity.ok(profesorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDTO> getProfesorById(@PathVariable Long id) {
        ProfesorDTO profesor = profesorService.findById(id);
        return profesor != null ? ResponseEntity.ok(profesor) : ResponseEntity.notFound().build();
    }
}
