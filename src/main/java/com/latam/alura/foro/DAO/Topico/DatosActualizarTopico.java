package com.latam.alura.foro.DAO.Topico;

import com.latam.alura.foro.Domains.Curso;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public record DatosActualizarTopico(
        @NotNull
        Long id,
        String titulo,
        String mensaje,
        String estado,
        Optional<Long> id_curso
) {
}
