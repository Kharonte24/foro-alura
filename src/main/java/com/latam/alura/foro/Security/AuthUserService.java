package com.latam.alura.foro.Security;

import com.latam.alura.foro.Repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AuthUserService(UsuarioRepository usuarioRepository){this.usuarioRepository = usuarioRepository;}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "email" + email + "not fount" ));
    }
}
