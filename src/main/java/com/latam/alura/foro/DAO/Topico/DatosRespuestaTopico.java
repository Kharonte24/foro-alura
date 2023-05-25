package com.latam.alura.foro.DAO.Topico;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.latam.alura.foro.DAO.Curso.DatosCurso;
import com.latam.alura.foro.DAO.Respuesta.DatosListaRespuestaTopico;
import com.latam.alura.foro.DAO.Usuario.DatosUsuario;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DatosRespuestaTopico(Long id, String titulo, String mensaje, String fechaCheacion, String estado,
                                   DatosUsuario autor, DatosCurso curso, List<DatosListaRespuestaTopico> respuestas ) {
}
