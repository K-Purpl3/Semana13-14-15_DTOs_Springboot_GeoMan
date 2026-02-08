package com.hogwarts.hogwartsapi.Casa;

import com.hogwarts.hogwartsapi.Repositorios.CasaRepository;
import com.hogwarts.hogwartsapi.DTO.CasaDTO.CasaDTO;
import com.hogwarts.hogwartsapi.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CasaService {

    private final CasaRepository casaRepository;

    @Autowired
    public CasaService(CasaRepository casaRepository) {
        this.casaRepository = casaRepository;
    }

    public List<CasaDTO> findAll() {
        return casaRepository.findAll().stream()
                .map(DtoMapper::toCasaDTO)
                .collect(Collectors.toList());
    }

    public CasaDTO findById(Long id) {
        return casaRepository.findById(id)
                .map(DtoMapper::toCasaDTO)
                .orElse(null);
    }
}
