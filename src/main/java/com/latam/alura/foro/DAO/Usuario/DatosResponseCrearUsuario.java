package com.latam.alura.foro.DAO.Usuario;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DatosResponseCrearUsuario(
        Long id,
        String nombre,
        String email,
        @JsonProperty("access_token") String jwtToken

) {
}
