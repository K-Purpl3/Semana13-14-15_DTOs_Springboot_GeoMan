package com.hogwarts.hogwartsapi.MascotaProfesor;

import com.hogwarts.hogwartsapi.Repositorios.MascotaRepository;
import com.hogwarts.hogwartsapi.DTO.MascotaDTO.MascotaDTO;
import com.hogwarts.hogwartsapi.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    @Autowired
    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public List<MascotaDTO> findAll() {
        return mascotaRepository.findAll().stream()
                .map(DtoMapper::toMascotaDTO)
                .collect(Collectors.toList());
    }

    public MascotaDTO findById(Long id) {
        return mascotaRepository.findById(id)
                .map(DtoMapper::toMascotaDTO)
                .orElse(null);
    }
}
