package com.hogwarts.hogwartsapi.mapper;

import com.hogwarts.hogwartsapi.Asignatura.Asignatura;
import com.hogwarts.hogwartsapi.Casa.Casa;
import com.hogwarts.hogwartsapi.DTO.AsignaturaDTO.AsignaturaCalificacionDTO;
import com.hogwarts.hogwartsapi.DTO.AsignaturaDTO.AsignaturaDTO;
import com.hogwarts.hogwartsapi.DTO.CasaDTO.CasaDTO;
import com.hogwarts.hogwartsapi.DTO.EstudianteDTO.EstudianteDTO;
import com.hogwarts.hogwartsapi.DTO.MascotaDTO.MascotaDTO;
import com.hogwarts.hogwartsapi.DTO.ProfeDTO.ProfesorDTO;
import com.hogwarts.hogwartsapi.EstudianteStuff.EstudianteAsignatura;
import com.hogwarts.hogwartsapi.EstudianteStuff.Estudiante;
import com.hogwarts.hogwartsapi.MascotaProfesor.Mascota;
import com.hogwarts.hogwartsapi.Profesor.Profesor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {

    // Casa -> CasaDTO
    public static CasaDTO toCasaDTO(Casa casa) {
        if (casa == null) return null;

        CasaDTO dto = new CasaDTO();
        dto.setId(casa.getId());
        dto.setNombre(casa.getNombre());
        dto.setFundador(casa.getFundador());
        dto.setFantasma(casa.getFantasma());
        
        if (casa.getProfesorJefe() != null) {
            dto.setJefe(toProfesorDTO(casa.getProfesorJefe()));
        }

        if (casa.getEstudiantes() != null) {
            List<String> nombresEstudiantes = casa.getEstudiantes().stream()
                    .map(est -> est.getNombre() + " " + est.getApellido())
                    .collect(Collectors.toList());
            dto.setEstudiantes(nombresEstudiantes);
        } else {
            dto.setEstudiantes(Collections.emptyList());
        }

        return dto;
    }

    //Profesor-ProfesorDTO
    public static ProfesorDTO toProfesorDTO(Profesor profesor) {
        if (profesor == null) return null;

        ProfesorDTO dto = new ProfesorDTO();
        dto.setId(profesor.getId());
        dto.setNombre(profesor.getNombre() + " " + profesor.getApellido());
        dto.setFechaInicio(profesor.getFechaInicio());
        
        if (profesor.getAsignatura() != null) {
            dto.setAsignatura(profesor.getAsignatura().getNombre());
        }

        return dto;
    }

    //Mascota-MascotaDTO
    public static MascotaDTO toMascotaDTO(Mascota mascota) {
        if (mascota == null) return null;

        MascotaDTO dto = new MascotaDTO();
        dto.setId(mascota.getId());
        dto.setNombre(mascota.getNombre());
        dto.setEspecie(mascota.getEspecie());
        
        if (mascota.getEstudiante() != null) {
            dto.setEstudiante(mascota.getEstudiante().getNombre() + " " + mascota.getEstudiante().getApellido());
        }

        return dto;
    }

    //EstudianteAsignatura-AsignaturaCalificacionDTO
    public static AsignaturaCalificacionDTO toAsignaturaCalificacionDTO(EstudianteAsignatura ea) {
        if (ea == null) return null;

        AsignaturaCalificacionDTO dto = new AsignaturaCalificacionDTO();
        if (ea.getAsignatura() != null) {
            dto.setAsignatura(ea.getAsignatura().getNombre());
        }
        dto.setCalificacion(ea.getCalificacion());

        return dto;
    }

    //Estudiante-EstudianteDTO
    public static EstudianteDTO toEstudianteDTO(Estudiante estudiante) {
        if (estudiante == null) return null;

        EstudianteDTO dto = new EstudianteDTO();
        dto.setId(estudiante.getId());
        dto.setNombre(estudiante.getNombre() + " " + estudiante.getApellido());
        dto.setAnyoCurso(estudiante.getAnyoCurso());
        dto.setFechaNacimiento(estudiante.getFechaNacimiento());
        
        if (estudiante.getCasa() != null) {
            dto.setCasa(estudiante.getCasa().getNombre());
        }

        //Se pilla la primera mascota del estudiante
        if (estudiante.getMascotas() != null && !estudiante.getMascotas().isEmpty()) {
            dto.setMascota(toMascotaDTO(estudiante.getMascotas().get(0)));
        }

        if (estudiante.getAsignaturasCursadas() != null) {
            List<AsignaturaCalificacionDTO> asignaturasDTO = estudiante.getAsignaturasCursadas().stream()
                    .map(DtoMapper::toAsignaturaCalificacionDTO)
                    .collect(Collectors.toList());
            dto.setAsignaturas(asignaturasDTO);
        } else {
            dto.setAsignaturas(Collections.emptyList());
        }

        return dto;
    }

    //Asignatura-AsignaturaDTO
    public static AsignaturaDTO toAsignaturaDTO(Asignatura asignatura) {
        if (asignatura == null) return null;

        AsignaturaDTO dto = new AsignaturaDTO();
        dto.setId(asignatura.getId());
        dto.setNombre(asignatura.getNombre());
        dto.setAula(asignatura.getAula());
        dto.setObligatoria(asignatura.getObligatoria());
        
        return dto;
    }
}
