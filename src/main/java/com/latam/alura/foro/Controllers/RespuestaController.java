package com.latam.alura.foro.Controllers;

import com.latam.alura.foro.DAO.Respuesta.DatosActualizarRespuesta;
import com.latam.alura.foro.DAO.Respuesta.DatosRegistroRespuesta;
import com.latam.alura.foro.DAO.Respuesta.DatosRespuesta;
import com.latam.alura.foro.Services.RespuestaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/respuesta")
public class RespuestaController {

    private final RespuestaService respuestaService;

    public RespuestaController(RespuestaService respuestaService){this.respuestaService = respuestaService;}


//REGISTRAR Nueva Respuesta
    @PostMapping
    public ResponseEntity<DatosRespuesta> registrarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistro,
                                          Authentication authentication,UriComponentsBuilder uriComponentsBuilder){
        var respuesta = this.respuestaService.registrarRespuesta(datosRegistro,authentication);

        URI src = uriComponentsBuilder.path("/respuesta/{id}").buildAndExpand(respuesta.id()).toUri();
        return ResponseEntity.created(src).body(respuesta);
    }

//BUSCAR Respuesta
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuesta> buscarRespuesta(@PathVariable Long id){
        return ResponseEntity.ok(this.respuestaService.buscarRespuesta(id));
    }

//ACTUALIZAR Respuesta
    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuesta> actualizarRespuesta(@RequestBody @Valid
           DatosActualizarRespuesta  datosActualizar, Authentication authentication){
        return ResponseEntity.ok(this.respuestaService.actualizarRespuesta(datosActualizar, authentication));
    }

//ELIMINAR Respuesta
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> borrarRespuesta(@PathVariable Long id, Authentication authentication){
        this.respuestaService.borrarRespuesta(id, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
