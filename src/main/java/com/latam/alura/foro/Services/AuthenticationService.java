package com.latam.alura.foro.Services;

import com.latam.alura.foro.DAO.Authentication.AuthRequest;
import com.latam.alura.foro.DAO.Authentication.AuthResponse;
import com.latam.alura.foro.DAO.Usuario.DatosRegistroUsuario;
import com.latam.alura.foro.DAO.Usuario.DatosResponseCrearUsuario;
import com.latam.alura.foro.Domains.Usuario;
import com.latam.alura.foro.Exeptions.DuplicateResourceException;
import com.latam.alura.foro.Repositories.UsuarioRepository;
import com.latam.alura.foro.Security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager, TokenService tokenService,
                                 UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(), request.contrasena()));

        Usuario principal = (Usuario) authentication.getPrincipal();
        String token = tokenService.generateToken(principal);

        return new AuthResponse(token);
    }

    public DatosResponseCrearUsuario register(DatosRegistroUsuario request){
        String email = request.email();
        if (usuarioRepository.existsUsuarioByEmail(email)){
            throw new DuplicateResourceException(
                    "email already taken"
            );
        }

        Usuario user = usuarioRepository.save(new Usuario(request, passwordEncoder));
        var token = tokenService.generateToken(user);

        return new DatosResponseCrearUsuario(user.getId(), user.getNombre(), user.getEmail(), token);
    }

}
