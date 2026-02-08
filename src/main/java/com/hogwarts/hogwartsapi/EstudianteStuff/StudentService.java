package com.hogwarts.hogwartsapi.EstudianteStuff;

import com.hogwarts.hogwartsapi.Repositorios.StudentRepository;
import com.hogwarts.hogwartsapi.dto.EstudianteDTO;
import com.hogwarts.hogwartsapi.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<EstudianteDTO> findAll() {
        return studentRepository.findAll().stream()
                .map(DtoMapper::toEstudianteDTO)
                .collect(Collectors.toList());
    }

    public EstudianteDTO findById(Long id) {
        return studentRepository.findById(id)
                .map(DtoMapper::toEstudianteDTO)
                .orElse(null);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
}
