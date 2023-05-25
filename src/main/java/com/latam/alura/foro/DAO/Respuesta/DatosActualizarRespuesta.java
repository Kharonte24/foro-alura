package com.latam.alura.foro.DAO.Respuesta;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarRespuesta(
        @NotNull
        Long id,
        String mensaje
) {
}
