package com.latam.alura.foro.Domains;

import com.latam.alura.foro.DAO.Respuesta.DatosActualizarRespuesta;
import com.latam.alura.foro.DAO.Respuesta.DatosRegistroRespuesta;
import jakarta.persistence.*;

import lombok.*;
import java.time.LocalDateTime;

@Table(name = "respuestas")
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @SequenceGenerator(name = "respuestaSequence",
    sequenceName = "respuestaSequence",
    allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "respuestaSequence")
    private Long id;

    private String mensaje;
    private LocalDateTime fechaCreacion;
    private Boolean solucion = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario autor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_topico")
    private Topico topico;

    public Respuesta(DatosRegistroRespuesta datosRegistroRespuesta, Usuario autor, Topico topico) {
        this.mensaje = datosRegistroRespuesta.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.autor = autor;
        this.topico = topico;
        this.solucion = solucion;
    }

    public void actualizaRespuesta(DatosActualizarRespuesta datosActualizarRespuesta){
        if (datosActualizarRespuesta.mensaje() !=null && !datosActualizarRespuesta.mensaje().isEmpty()){
            this.mensaje = datosActualizarRespuesta.mensaje();
        }
    }

    public void finalizarRespuesta(){this.solucion =true;}
}
