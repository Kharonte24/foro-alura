package com.latam.alura.foro.Services;

import com.latam.alura.foro.DAO.Curso.DatosCurso;
import com.latam.alura.foro.DAO.Respuesta.DatosListaRespuestaTopico;
import com.latam.alura.foro.DAO.Topico.*;
import com.latam.alura.foro.DAO.Usuario.DatosUsuario;
import com.latam.alura.foro.Domains.Curso;
import com.latam.alura.foro.Domains.Topico;
import com.latam.alura.foro.Domains.Usuario;
import com.latam.alura.foro.Exeptions.ResourceNotFoundException;
import com.latam.alura.foro.Repositories.CursoRepository;
import com.latam.alura.foro.Repositories.TopicoRepository;
import com.latam.alura.foro.Repositories.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    public TopicoService(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository,
                         CursoRepository cursoRepository){
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }
//Create Topico
    public DatosTopico registrarTopico(DatosRegistroTopico datosRegistroTopico, Authentication authentication) {
        Usuario autor = this.usuarioRepository.getReferenceById(datosRegistroTopico.id_autor());
        Curso curso = this.cursoRepository.getReferenceById(datosRegistroTopico.id_curso());

        validarAuthenticationUsuario(datosRegistroTopico.id_autor(), authentication,"No " +
                "Tiene Permiso para Crear un Topico");
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico,autor,curso));

        return factoryDatosTopico(topico);
    }

//Read Topico
    public Page<DatosListadoTopico> listadoTopicos(Pageable pageable){
        return this.topicoRepository.findAll(pageable).map(DatosListadoTopico::new);
    }

//getByID
    public DatosRespuestaTopico buscarTopico(Long id){
        Topico  topico = this.topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topico no encontrado"));
        DatosTopico datosTopico = factoryDatosTopico(topico);

        return new DatosRespuestaTopico(datosTopico.id(), datosTopico.titulo(), datosTopico.mensaje(),
                datosTopico.fechaCreacion(), datosTopico.estado(), datosTopico.autor(), datosTopico.curso(),
                factoryDatosListaRespuestaTopico(topico));
    }

    private List<DatosListaRespuestaTopico> factoryDatosListaRespuestaTopico(Topico topico) {
        return topico.getRespuestas().stream().map(DatosListaRespuestaTopico::new).collect(Collectors.toList());
    }

//Update Topico
    public DatosTopico actualizarTopico(DatosActualizarTopico datosActualizar, Authentication authentication){
        Topico topico = this.topicoRepository.getReferenceById(datosActualizar.id());
        validarAuthenticationUsuario(topico.getAutor().getId(),authentication, "No tiene" +
                "Permisos para actualizar el topico");
        if (datosActualizar.id_curso().isEmpty()){
            topico.actualizaTopico(datosActualizar,null);
        }
        else{
            Curso curso = this.cursoRepository.getReferenceById(datosActualizar.id_curso().get());
            topico.actualizaTopico(datosActualizar,curso);
        }
        return factoryDatosTopicoDisponible(topico);
    }

//Delete Topico
    public void eliminarTopico(Long id, Authentication authentication){
        Topico topico =this.topicoRepository.getReferenceById(id);
        validarAuthenticationUsuario(topico.getAutor().getId(), authentication,"No Tiene " +
                "Permiso para realizar esta Operacion");
        this.topicoRepository.delete(topico);
    }

    private DatosTopico factoryDatosTopicoDisponible(Topico topico) {
        return  new DatosTopico(
                topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getFechaCreacion().toString(), topico.getEstado().toString(),
                null, factoryDatosCurso(topico)
        );
    }

    private DatosTopico factoryDatosTopico(Topico topico){
        return new DatosTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getFechaCreacion().toString(), topico.getEstado().toString(),
                factoryDatosUsuario(topico), factoryDatosCurso(topico)
        );

    }

    private DatosCurso factoryDatosCurso(Topico topico) {
        return new DatosCurso(topico.getCurso().getId(), topico.getCurso().getNombre(),
                              topico.getCurso().getCategoria()
        );
    }

    private DatosUsuario factoryDatosUsuario(Topico topico) {
        return new DatosUsuario(topico.getAutor().getId(), topico.getAutor().getNombre(),
                                topico.getAutor().getEmail()
        );
    }

    private void validarAuthenticationUsuario(Long autorID,Authentication authentication, String mensaje){
        Usuario authenticateUser = (Usuario) authentication.getPrincipal();
        if (!autorID.equals(authenticateUser.getId())){
            throw new BadCredentialsException(mensaje);
        }
    }
}
