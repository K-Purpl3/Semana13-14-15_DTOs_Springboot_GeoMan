package com.hogwarts.hogwartsapi.Repositorios;


//import com.hogwarts.model.Student;
import com.hogwarts.hogwartsapi.EstudianteStuff.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByCasaNombre(String nombreCasa);
    List<Student> findByAnyoCurso(Integer anyoCurso);
}