package com.latam.alura.foro.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.latam.alura.foro.Domains.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.secret}")
    private String SECRET_KEY;

    public String generateToken(Usuario user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer("ALURA-FORO")
                    .withSubject(user.getUsername())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("error creating token");
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

    public String validateToken(String jwtToken) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer("ALURA-FORO")
                    .build();

            DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);

            if (decodedJWT.getSubject() == null) {
                throw new RuntimeException("invalid token: missing subject");
            }
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("invalid token or has expired");
        }
    }
}
