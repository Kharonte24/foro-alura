package com.latam.alura.foro.DAO.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DatosRegistroUsuario(
        @NotBlank
        @Size(max = 80)
        String nombre,
        @NotBlank
        @Email
        @Size(max = 100)
        String email,
        @NotBlank
        String contrasena
) {
}
