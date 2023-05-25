package com.latam.alura.foro.DAO.Curso;

import com.latam.alura.foro.Domains.Curso;

public record DatosListadoCurso(Long id, String nombre, String categoria) {

    public DatosListadoCurso(Curso curso){this(curso.getId(), curso.getNombre(), curso.getCategoria());}
}
