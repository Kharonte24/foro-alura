package com.latam.alura.foro.Services;

import com.latam.alura.foro.DAO.Curso.DatosActualizarCurso;
import com.latam.alura.foro.DAO.Curso.DatosCurso;
import com.latam.alura.foro.DAO.Curso.DatosListadoCurso;
import com.latam.alura.foro.DAO.Curso.DatosRegistroCurso;
import com.latam.alura.foro.Domains.Curso;
import com.latam.alura.foro.Exeptions.ResourceNotFoundException;
import com.latam.alura.foro.Repositories.CursoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository){this.cursoRepository = cursoRepository;}

//CREATE Curso
    public DatosCurso registrarCurso(DatosRegistroCurso datosRegistroCurso){
        Curso curso = this.cursoRepository.save(new Curso(datosRegistroCurso));
        return factoryDatosCurso(curso);
    }

//READ Cursos
    public Page<DatosListadoCurso>  listadoCursos(@PageableDefault(size = 25) Pageable pageable){
    Page<Curso> cursos = this.cursoRepository.findByEstadoTrue(pageable);
    return cursos.map(DatosListadoCurso::new);
    }
//GET Curso ByID
    public ResponseEntity<DatosCurso> buscarCurso(@PathVariable Long id){
        Curso curso = this.cursoRepository.findByIdAndEstadoTrue(id)
                .orElseThrow(() -> new  ResourceNotFoundException("Curso No Encontrado"));
        var datosCurso = new DatosCurso(curso.getId(), curso.getNombre(), curso.getCategoria());

        return ResponseEntity.ok(datosCurso);
    }
//UPDATE Curso
    public DatosCurso actualizaCurso(DatosActualizarCurso datosActualizar){
        Curso curso = this.cursoRepository.getReferenceById(datosActualizar.id());
        curso.actualizaCurso(datosActualizar);

        return factoryDatosCurso(curso);
    }

//DELETE LOGIC Curso
    public void eliminarCurso(Long id){
        Curso curso = this.cursoRepository.getReferenceById(id);
        curso.desactivarCurso();
    }

    private DatosCurso factoryDatosCurso(Curso curso) {
        return new DatosCurso(curso.getId(), curso.getNombre(), curso.getCategoria());
    }

}
