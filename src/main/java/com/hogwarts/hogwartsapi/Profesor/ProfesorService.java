package com.hogwarts.hogwartsapi.Profesor;

import com.hogwarts.hogwartsapi.Repositorios.ProfesorRepository;
import com.hogwarts.hogwartsapi.DTO.ProfeDTO.ProfesorDTO;
import com.hogwarts.hogwartsapi.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfesorService {

    private final ProfesorRepository profesorRepository;

    @Autowired
    public ProfesorService(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    public List<ProfesorDTO> findAll() {
        return profesorRepository.findAll().stream()
                .map(DtoMapper::toProfesorDTO)
                .collect(Collectors.toList());
    }

    public ProfesorDTO findById(Long id) {
        return profesorRepository.findById(id)
                .map(DtoMapper::toProfesorDTO)
                .orElse(null);
    }
}
