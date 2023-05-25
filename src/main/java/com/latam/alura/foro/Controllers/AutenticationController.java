package com.latam.alura.foro.Controllers;

import com.latam.alura.foro.DAO.Authentication.AuthRequest;
import com.latam.alura.foro.DAO.Authentication.AuthResponse;
import com.latam.alura.foro.DAO.Errores.ErrorAPI;
import com.latam.alura.foro.DAO.Usuario.DatosRegistroUsuario;
import com.latam.alura.foro.DAO.Usuario.DatosResponseCrearUsuario;
import com.latam.alura.foro.DAO.Usuario.DatosUsuario;
import com.latam.alura.foro.Services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/authentication")
public class AutenticationController {

    private final AuthenticationService authenticationService;

    public AutenticationController(AuthenticationService authenticationService){this.authenticationService = authenticationService;}

    @Operation(
            summary = "log in to the application",
            description = "logs a user into the application",
            security = @SecurityRequirement(name = "noAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Authentication successful",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Bad credentials",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorAPI.class))
                    )
            }
    )
    @SecurityRequirements()
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request) {

        AuthResponse response = authenticationService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, response.jwtToken()).body(response);
    }

    @Operation(
            summary = "register a new user",
            description = "registers a new user with the application",
            security = @SecurityRequirement(name = "noAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DatosUsuario.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorAPI.class))),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflict: email already taken",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorAPI.class)))
            }
    )
    @SecurityRequirements()
    @PostMapping("/register")
    public ResponseEntity<DatosResponseCrearUsuario> saveUser(
            @RequestBody @Valid DatosRegistroUsuario request,
            UriComponentsBuilder uriComponentsBuilder) {

        DatosResponseCrearUsuario dataResponseUser = authenticationService.register(request);

        URI url = uriComponentsBuilder.path("/register/{id}").buildAndExpand(dataResponseUser.id()).toUri();

        return ResponseEntity.created(url)
                .header("Authorization", "Bearer " + dataResponseUser.jwtToken())
                .body(dataResponseUser);
    }



}
