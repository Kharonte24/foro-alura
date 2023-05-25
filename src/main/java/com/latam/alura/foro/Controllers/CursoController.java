package com.latam.alura.foro.Controllers;

import com.latam.alura.foro.DAO.Curso.DatosActualizarCurso;
import com.latam.alura.foro.DAO.Curso.DatosCurso;
import com.latam.alura.foro.DAO.Curso.DatosListadoCurso;
import com.latam.alura.foro.DAO.Curso.DatosRegistroCurso;
import com.latam.alura.foro.Services.CursoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/curso")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService){this.cursoService = cursoService;}

// Registrar Curso
    @PostMapping
    public ResponseEntity<DatosCurso> registrarCurso(@RequestBody @Valid DatosRegistroCurso datosRegistro,
                                                    UriComponentsBuilder uriComponentsBuilder){
        var curso = this.cursoService.registrarCurso(datosRegistro);
        URI src =uriComponentsBuilder.path("/curso/{id}").buildAndExpand(curso.id()).toUri();

        return ResponseEntity.created(src).body(curso);
    }

// Actualizar Curso
    @PutMapping
    @Transactional
    public ResponseEntity<DatosCurso> actualizarCurso(@RequestBody @Valid DatosActualizarCurso datosActualizar){
        return ResponseEntity.ok().body(this.cursoService.actualizaCurso(datosActualizar));
    }

//Listar Cursos
    @GetMapping
    public Page<DatosListadoCurso> listadoCursos(Pageable pageable){
       return this.cursoService.listadoCursos(pageable);
    }

//Buscar Curso
    @GetMapping("/{id}")
    public ResponseEntity<DatosCurso> buscarCurso(@PathVariable Long id){
        return this.cursoService.buscarCurso(id);
    }

//Eliminar Curso
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminarCurso(@PathVariable Long id){
        this.cursoService.eliminarCurso(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
