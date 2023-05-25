package com.latam.alura.foro.DAO.Errores;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorAPI(
        String path,
        String mensaje,
        int statusCode,
        LocalDateTime localDateTime,
        List<ErrorValidacion> errores
) {
}
