package com.latam.alura.foro.DAO.Respuesta;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.latam.alura.foro.DAO.Usuario.DatosUsuario;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DatosRespuesta(Long id, String mensaje, String fechaCreacion,
                             DatosUsuario autor, Boolean solucion, Long topico) {
}
