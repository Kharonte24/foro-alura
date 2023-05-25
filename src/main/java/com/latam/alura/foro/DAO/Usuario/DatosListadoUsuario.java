package com.latam.alura.foro.DAO.Usuario;

import com.latam.alura.foro.Domains.Usuario;

public record DatosListadoUsuario(Long id, String nombre, String email) {
    public DatosListadoUsuario(Usuario user){this(user.getId(), user.getNombre(), user.getEmail());}
}
