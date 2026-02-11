package com.hogwarts.hogwartsapi.Profesor;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hogwarts.hogwartsapi.Asignatura.Asignatura;
import com.hogwarts.hogwartsapi.Casa.Casa;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "Profesor")
@Data
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesor")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @ManyToOne
    @JoinColumn(name = "id_asignatura", referencedColumnName = "id_asignatura")
    private Asignatura asignatura;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @OneToOne
    @JoinColumn(name = "id_casa_jefe")
    @JsonManagedReference("profesor-casa")
    private Casa casaComoJefe;
}