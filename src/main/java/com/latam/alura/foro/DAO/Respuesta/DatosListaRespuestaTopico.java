package com.latam.alura.foro.DAO.Respuesta;

import com.latam.alura.foro.DAO.Usuario.DatosUsuario;
import com.latam.alura.foro.Domains.Respuesta;

public record DatosListaRespuestaTopico(Long id, String mensaje, String fechaCreacion,
                                        DatosUsuario autor, Boolean solucion) {

    public DatosListaRespuestaTopico(Respuesta respuesta){
            this(respuesta.getId(), respuesta.getMensaje(), respuesta.getFechaCreacion().toString(),
                new DatosUsuario(respuesta.getAutor().getId(), respuesta.getAutor().getNombre(),
                        respuesta.getAutor().getEmail()), respuesta.getSolucion());
    }
}
