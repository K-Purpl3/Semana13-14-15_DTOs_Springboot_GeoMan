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
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> getStudentById(@PathVariable Long id) {
        EstudianteDTO student = studentService.findById(id);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EstudianteDTO> createStudent(@Valid @RequestBody EstudianteCreateDTO createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(createDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody EstudianteUpdateDTO updateDTO) {
        return ResponseEntity.ok(studentService.updateStudent(id, updateDTO));
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
