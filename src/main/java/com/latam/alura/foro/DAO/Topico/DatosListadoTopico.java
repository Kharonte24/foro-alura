package com.latam.alura.foro.DAO.Topico;

import com.latam.alura.foro.DAO.Curso.DatosCurso;
import com.latam.alura.foro.DAO.Usuario.DatosUsuario;
import com.latam.alura.foro.Domains.Topico;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        String fechaCreacion,
        DatosUsuario autor,
        DatosCurso curso
) {
    public DatosListadoTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion().toString(),
        new DatosUsuario(topico.getAutor().getId(), topico.getAutor().getNombre(),
                topico.getAutor().getEmail()),
        new DatosCurso(topico.getCurso().getId(), topico.getCurso().getNombre(),
                topico.getCurso().getCategoria()
        ));

    }
}
