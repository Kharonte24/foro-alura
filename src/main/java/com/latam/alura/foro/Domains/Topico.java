package com.latam.alura.foro.Domains;

import com.latam.alura.foro.DAO.Topico.DatosActualizarTopico;
import com.latam.alura.foro.DAO.Topico.DatosRegistroTopico;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Table(name = "topicos")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @SequenceGenerator(
            name = "topicoSequence",
            sequenceName = "topicoSequence",
            allocationSize = 1

    )
    @GeneratedValue(strategy = SEQUENCE, generator = "topicoSequence")
    private Long id;
    private String titulo;
    private String mensaje;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    @Enumerated(EnumType.STRING)
    private StatusTopico estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso")
    private Curso curso;

    @OneToMany(mappedBy = "topico",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> respuestas = new ArrayList<>();


    public Topico(DatosRegistroTopico datosRegistroTopico, Usuario autor, Curso curso) {
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.estado = StatusTopico.NO_RESPONDIDO;
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
        this.autor = autor;
        this.curso = curso;
    }

    public void actualizaTopico(DatosActualizarTopico datosActualizarTopico, Curso curso){

        if (datosActualizarTopico.titulo() != null && !datosActualizarTopico.titulo().isEmpty()){
            this.titulo = datosActualizarTopico.titulo();
        }
        if (datosActualizarTopico.mensaje() != null && !datosActualizarTopico.mensaje().isEmpty()){
            this.mensaje = datosActualizarTopico.mensaje();
        }
        if (datosActualizarTopico.estado() != null){
            this.estado = StatusTopico.valueOf(datosActualizarTopico.estado());
        }
        if (datosActualizarTopico.id_curso().isPresent()){
            this.curso = curso;
        }
    }

    public void setEstadoNoSolucionado(){ this.estado = StatusTopico.NO_SOLUCIONADO;}

    public Boolean cerrado(){
        return estado == StatusTopico.CERRADO || estado == StatusTopico.SOLUCIONADO;
    }

}
