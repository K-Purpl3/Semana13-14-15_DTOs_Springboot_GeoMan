package com.hogwarts.hogwartsapi.Asignatura;

import com.hogwarts.hogwartsapi.Repositorios.ProfesorRepository;
import com.hogwarts.hogwartsapi.Repositorios.AsignaturaRepository;
import com.hogwarts.hogwartsapi.dto.AsignaturaDTO;
import com.hogwarts.hogwartsapi.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignaturaService {

    private final AsignaturaRepository asignaturaRepository;
    private final ProfesorRepository profesorRepository; // Para buscar el profesor

    @Autowired
    public AsignaturaService(AsignaturaRepository asignaturaRepository, ProfesorRepository profesorRepository) {
        this.asignaturaRepository = asignaturaRepository;
        this.profesorRepository = profesorRepository;
    }

    public List<AsignaturaDTO> findAll() {
        return asignaturaRepository.findAll().stream()
                .map(this::mapToDtoWithProfesor)
                .collect(Collectors.toList());
    }

    public AsignaturaDTO findById(Long id) {
        return asignaturaRepository.findById(id)
                .map(this::mapToDtoWithProfesor)
                .orElse(null);
    }

    private AsignaturaDTO mapToDtoWithProfesor(Asignatura asignatura) {
        AsignaturaDTO dto = DtoMapper.toAsignaturaDTO(asignatura);
        //buscar el profesor que imparte esta asignatura
        //asumo que solo un profesor imparte una asignatura para simplificar
        profesorRepository.findAll().stream()
                .filter(p -> p.getAsignatura() != null && p.getAsignatura().getId().equals(asignatura.getId()))
                .findFirst()
                .ifPresent(profesor -> dto.setProfesor(profesor.getNombre() + " " + profesor.getApellido()));
        return dto;
    }
}
