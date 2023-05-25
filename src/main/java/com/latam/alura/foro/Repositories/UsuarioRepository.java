package com.latam.alura.foro.Repositories;

import com.latam.alura.foro.Domains.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario>findByEmail(String email);
    Boolean existsUsuarioByEmail(String email);
}
