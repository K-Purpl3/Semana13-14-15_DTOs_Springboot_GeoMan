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
public class StudentService {
    private final StudentRepository studentRepository;
    private final CasaRepository casaRepository;
    private final MascotaRepository mascotaRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CasaRepository casaRepository, MascotaRepository mascotaRepository) {
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

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void deleteById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Estudiante no encontrado");
        }
        studentRepository.deleteById(id);
    }


    @Transactional
    public EstudianteDTO createStudent(EstudianteCreateDTO createDTO) {
        Casa casa = casaRepository.findById(createDTO.getCasaId())
                .orElseThrow(() -> new EntityNotFoundException("Casa no encontrada"));
                                        // cambio de exception correcto

        Student student = new Student();
        student.setNombre(createDTO.getNombre());
        student.setApellido(createDTO.getApellido());
        student.setAnyoCurso(createDTO.getAnyoCurso());
        student.setFechaNacimiento(createDTO.getFechaNacimiento());
        student.setCasa(casa);

        Student savedStudent = studentRepository.save(student);

        if (createDTO.getMascota() != null) {
            Mascota mascota = new Mascota();
            mascota.setNombre(createDTO.getMascota().getNombre());
            mascota.setEspecie(createDTO.getMascota().getEspecie());
            mascota.setEstudiante(savedStudent);
            mascotaRepository.save(mascota);
            
            // Actualizar la lista de mascotas del estudiante para el retorno
            List<Mascota> mascotas = new ArrayList<>();
            mascotas.add(mascota);
            savedStudent.setMascotas(mascotas);
        }

        return DtoMapper.toEstudianteDTO(savedStudent);
    }

    @Transactional
    public EstudianteDTO updateStudent(Long id, EstudianteUpdateDTO updateDTO) {

            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));
            //cambiado por entitynotfound para lanzar errores correctos

            student.setAnyoCurso(updateDTO.getAnyoCurso());
            student.setFechaNacimiento(updateDTO.getFechaNacimiento());

            //manejo de mascota
            if (updateDTO.getMascota() != null) {
                // Si hay nueva mascota, reemplazar o crear
                if (student.getMascotas() != null && !student.getMascotas().isEmpty()) {
                    // Actualizar la existente (asumimos que solo tiene una por el enunciado)
                    Mascota mascotaExistente = student.getMascotas().get(0);
                    mascotaExistente.setNombre(updateDTO.getMascota().getNombre());
                    mascotaExistente.setEspecie(updateDTO.getMascota().getEspecie());
                    mascotaRepository.save(mascotaExistente);
                } else {
                    //crear nueva
                    Mascota nuevaMascota = new Mascota();
                    nuevaMascota.setNombre(updateDTO.getMascota().getNombre());
                    nuevaMascota.setEspecie(updateDTO.getMascota().getEspecie());
                    nuevaMascota.setEstudiante(student);
                    mascotaRepository.save(nuevaMascota);

                    // Actualizar referencia en memoria
                    if (student.getMascotas() == null) {
                        student.setMascotas(new ArrayList<>());
                    }
                    student.getMascotas().add(nuevaMascota);
                }
            } else {
                // Si mascota es null en el DTO, eliminar la mascota existente (null) si la hay
                if (student.getMascotas() != null && !student.getMascotas().isEmpty()) {
                    Mascota mascotaAEliminar = student.getMascotas().get(0);
                    mascotaRepository.delete(mascotaAEliminar);
                    student.getMascotas().clear();
                }
            }

            Student updatedStudent = studentRepository.save(student);
            return DtoMapper.toEstudianteDTO(updatedStudent);
    }
}
