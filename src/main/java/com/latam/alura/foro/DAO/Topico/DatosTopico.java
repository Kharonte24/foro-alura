package com.latam.alura.foro.DAO.Topico;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.latam.alura.foro.DAO.Curso.DatosCurso;
import com.latam.alura.foro.DAO.Usuario.DatosUsuario;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DatosTopico(
        Long id,
        String titulo,
        String mensaje,
        String fechaCreacion,
        String estado,
        DatosUsuario autor,
        DatosCurso curso

) {
}
