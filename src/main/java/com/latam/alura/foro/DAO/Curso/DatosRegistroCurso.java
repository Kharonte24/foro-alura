package com.latam.alura.foro.DAO.Curso;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroCurso(
        @NotBlank
        String nombre,
        @NotBlank
        String categoria
) {
}
