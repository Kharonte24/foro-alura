package com.latam.alura.foro.Domains;

import com.latam.alura.foro.DAO.Curso.DatosActualizarCurso;
import com.latam.alura.foro.DAO.Curso.DatosRegistroCurso;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "cursos")
@Entity
public class Curso {
    @Id
    @SequenceGenerator(name = "cursoSequence",
    sequenceName = "cursoSequence",
    allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cursoSequence")
    private Long id;
    private String nombre;
    private String categoria;
    private Boolean estado;
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topico> topicos = new ArrayList<>();


    public Curso(DatosRegistroCurso datosRegistroCurso) {
        this.nombre = datosRegistroCurso.nombre();
        this.categoria = datosRegistroCurso.categoria();
        this.estado = true;
    }

    public void actualizaCurso(DatosActualizarCurso datosActualizarCurso){
        if (datosActualizarCurso.nombre() != null && !datosActualizarCurso.nombre().isEmpty()){
            this.nombre = datosActualizarCurso.nombre();
        }
        if (datosActualizarCurso.categoria() != null && !datosActualizarCurso.categoria().isEmpty()) {
            this.categoria = datosActualizarCurso.categoria();
        }
    }

    public void desactivarCurso(){this.estado = false;}


}
