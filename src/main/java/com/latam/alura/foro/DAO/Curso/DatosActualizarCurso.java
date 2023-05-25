package com.latam.alura.foro.DAO.Curso;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarCurso(
        @NotNull
        Long id,
        String nombre,
        String categoria
) {
}
