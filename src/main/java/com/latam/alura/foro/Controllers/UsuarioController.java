package com.latam.alura.foro.Controllers;

import com.latam.alura.foro.DAO.Usuario.DatosListadoUsuario;
import com.latam.alura.foro.DAO.Usuario.DatosUsuario;
import com.latam.alura.foro.Domains.Usuario;
import com.latam.alura.foro.Repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository){this.usuarioRepository = usuarioRepository;}

    @Operation(
            summary = "gets a list of all users",
            tags = {"user"}
    )
    @GetMapping
    public Page<DatosListadoUsuario> getAllUsers(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<Usuario> users = usuarioRepository.findAll(pageable);
        return users.map(DatosListadoUsuario::new);
    }

    @Operation(
            summary = "gets a user by id",
            tags = {"user"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<DatosUsuario> getUserById(
            @PathVariable Long id) {
        Usuario user = usuarioRepository.getReferenceById(id);
        var dataUser = new DatosUsuario(
                user.getId(), user.getNombre(), user.getEmail());

        return ResponseEntity.ok(dataUser);
    }
}
