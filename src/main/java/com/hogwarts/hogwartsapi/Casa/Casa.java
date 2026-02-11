package com.hogwarts.hogwartsapi.Casa;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hogwarts.hogwartsapi.EstudianteStuff.Estudiante;
import com.hogwarts.hogwartsapi.Profesor.Profesor;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "casa")
@Data
public class Casa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_casa")
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "fundador", nullable = false)
    private String fundador;

    @Column(name = "fantasma", nullable = false)
    private String fantasma;

    @OneToMany(mappedBy = "casa")
    @JsonManagedReference("casa-estudiantes")
    private List<Estudiante> estudiantes;

    @OneToOne(mappedBy = "casaComoJefe")
    @JsonBackReference("profesor-casa")
    private Profesor profesorJefe;
}
