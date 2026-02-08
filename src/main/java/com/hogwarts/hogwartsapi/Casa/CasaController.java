package com.hogwarts.hogwartsapi.Casa;

import com.hogwarts.hogwartsapi.DTO.CasaDTO.CasaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/casas")
public class CasaController {

    private final CasaService casaService;

    @Autowired
    public CasaController(CasaService casaService) {
        this.casaService = casaService;
    }

    @GetMapping
    public ResponseEntity<List<CasaDTO>> getAllCasas() {
        return ResponseEntity.ok(casaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CasaDTO> getCasaById(@PathVariable Long id) {
        CasaDTO casa = casaService.findById(id);
        return casa != null ? ResponseEntity.ok(casa) : ResponseEntity.notFound().build();
    }
}
