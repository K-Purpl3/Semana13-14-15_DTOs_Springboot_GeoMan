package com.hogwarts.hogwartsapi.EstudianteStuff;

import com.hogwarts.hogwartsapi.Casa.Casa;
import com.hogwarts.hogwartsapi.MascotaProfesor.Mascota;
import com.hogwarts.hogwartsapi.Repositorios.CasaRepository;
import com.hogwarts.hogwartsapi.Repositorios.MascotaRepository;
import com.hogwarts.hogwartsapi.Repositorios.StudentRepository;
import com.hogwarts.hogwartsapi.DTO.EstudianteDTO.EstudianteCreateDTO;
import com.hogwarts.hogwartsapi.DTO.EstudianteDTO.EstudianteDTO;
import com.hogwarts.hogwartsapi.DTO.EstudianteDTO.EstudianteUpdateDTO;
import com.hogwarts.hogwartsapi.mapper.DtoMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteService {
    private final StudentRepository studentRepository;
    private final CasaRepository casaRepository;
    private final MascotaRepository mascotaRepository;



    //garantiza consistencia en las operaciones de bbdd
    @Autowired
    public EstudianteService(StudentRepository studentRepository, CasaRepository casaRepository, MascotaRepository mascotaRepository) {
        this.studentRepository = studentRepository;
        this.casaRepository = casaRepository;
        this.mascotaRepository = mascotaRepository;
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

    public Estudiante save(Estudiante estudiante) {
        return studentRepository.save(estudiante);
    }

    //transactional crea una transicion, o todo se guarda o no se guarda nada si hay algun error
    @Transactional
    public void deleteById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Estudiante no encontrado");
        }
        studentRepository.deleteById(id);
    }
    //si exite se borra
    //si no existe lanza un 404
    //las mascotas se borran por cascade


    @Transactional
    public EstudianteDTO createStudent(EstudianteCreateDTO createDTO) {
        Casa casa = casaRepository.findById(createDTO.getCasaId())
                .orElseThrow(() -> new EntityNotFoundException("Casa no encontrada"));
                                        // cambio de exception correcto

        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(createDTO.getNombre());
        estudiante.setApellido(createDTO.getApellido());
        estudiante.setAnyoCurso(createDTO.getAnyoCurso());
        estudiante.setFechaNacimiento(createDTO.getFechaNacimiento());
        estudiante.setCasa(casa);

        Estudiante savedEstudiante = studentRepository.save(estudiante);

        if (createDTO.getMascota() != null) {
            Mascota mascota = new Mascota();
            mascota.setNombre(createDTO.getMascota().getNombre());
            mascota.setEspecie(createDTO.getMascota().getEspecie());
            mascota.setEstudiante(savedEstudiante);
            mascotaRepository.save(mascota);
            
            // Actualizar la lista de mascotas del estudiante para el retorno
            List<Mascota> mascotas = new ArrayList<>();
            mascotas.add(mascota);
            savedEstudiante.setMascotas(mascotas);
        }

        return DtoMapper.toEstudianteDTO(savedEstudiante);
    }

    @Transactional
    public EstudianteDTO updateStudent(Long id, EstudianteUpdateDTO updateDTO) {

            Estudiante estudiante = studentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));
            //cambiado por entitynotfound para lanzar errores correctos

            estudiante.setAnyoCurso(updateDTO.getAnyoCurso());
            estudiante.setFechaNacimiento(updateDTO.getFechaNacimiento());

            //manejo de mascota
            if (updateDTO.getMascota() != null) {
                // Si hay nueva mascota, reemplazar o crear
                if (estudiante.getMascotas() != null && !estudiante.getMascotas().isEmpty()) {
                    // Actualizar la existente (asumimos que solo tiene una por el enunciado)
                    Mascota mascotaExistente = estudiante.getMascotas().get(0);
                    mascotaExistente.setNombre(updateDTO.getMascota().getNombre());
                    mascotaExistente.setEspecie(updateDTO.getMascota().getEspecie());
                    mascotaRepository.save(mascotaExistente);
                } else {
                    //crear nueva
                    Mascota nuevaMascota = new Mascota();
                    nuevaMascota.setNombre(updateDTO.getMascota().getNombre());
                    nuevaMascota.setEspecie(updateDTO.getMascota().getEspecie());
                    nuevaMascota.setEstudiante(estudiante);
                    mascotaRepository.save(nuevaMascota);

                    // Actualizar referencia en memoria
                    if (estudiante.getMascotas() == null) {
                        estudiante.setMascotas(new ArrayList<>());
                    }
                    estudiante.getMascotas().add(nuevaMascota);
                }
            } else {
                // Si mascota es null en el DTO, eliminar la mascota existente (null) si la hay
                if (estudiante.getMascotas() != null && !estudiante.getMascotas().isEmpty()) {
                    Mascota mascotaAEliminar = estudiante.getMascotas().get(0);
                    mascotaRepository.delete(mascotaAEliminar);
                    estudiante.getMascotas().clear();
                }
            }

            Estudiante updatedEstudiante = studentRepository.save(estudiante);
            return DtoMapper.toEstudianteDTO(updatedEstudiante);
    }
}
