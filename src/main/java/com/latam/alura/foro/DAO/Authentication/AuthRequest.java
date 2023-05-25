package com.latam.alura.foro.DAO.Authentication;

public record AuthRequest(
        String email,
        String contrasena
) {
}
