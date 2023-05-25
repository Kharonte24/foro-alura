package com.latam.alura.foro.Controllers;


import com.latam.alura.foro.DAO.Topico.*;
import com.latam.alura.foro.Services.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topico")
public class TopicoController {

    private final TopicoService topicoService;

    public TopicoController(TopicoService topicoService){this.topicoService = topicoService;}

// Registro de un Nuevo Topico
    @PostMapping
    public ResponseEntity<DatosTopico> registrarTopico(
            @Valid @RequestBody DatosRegistroTopico datosRegistroTopico,
            Authentication authentication,
            UriComponentsBuilder uriComponentsBuilder){
        var topico =this.topicoService.registrarTopico(datosRegistroTopico, authentication);
        URI src = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topico.id()).toUri();
        return ResponseEntity.created(src).body(topico);
    }


// Mostrar todos los Topicos
    @GetMapping
    public Page<DatosListadoTopico> listarTopicos(@PageableDefault(size =25,sort = "titulo") Pageable pageable){
        return this.topicoService.listadoTopicos(pageable);
    }

// Buscar un Topico
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> buscarTopico(@PathVariable Long id){
        return ResponseEntity.ok(this.topicoService.buscarTopico(id));
    }

// Modificar un Topico
    @PutMapping
    @Transactional
    public ResponseEntity<DatosTopico> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizar,
                                                        Authentication authentication){
        return ResponseEntity.ok(this.topicoService.actualizarTopico(datosActualizar, authentication));
    }

// Eliminar un Topico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id, Authentication authentication){
        this.topicoService.eliminarTopico(id, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
