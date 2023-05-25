package com.latam.alura.foro.Domains;

import com.latam.alura.foro.DAO.Usuario.DatosRegistroUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @SequenceGenerator(name = "usuarioSequence",
    sequenceName = "usuarioSequence",
    allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarioSequence")
    private Long id;
    private String nombre;
    @Column(name ="email",unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Roles roll;
    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topico> topicos = new ArrayList<>();
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topico> respuestas = new ArrayList<>();

    public Usuario(DatosRegistroUsuario datosRegistroUsuario, PasswordEncoder passwordEncoder) {
        this.nombre = datosRegistroUsuario.nombre();
        this.email = datosRegistroUsuario.email();
        this.password = passwordEncoder.encode(datosRegistroUsuario.contrasena());
        this.roll = Roles.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roll.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
