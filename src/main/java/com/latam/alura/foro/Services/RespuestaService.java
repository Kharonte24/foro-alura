package com.latam.alura.foro.Services;

import com.latam.alura.foro.DAO.Respuesta.DatosActualizarRespuesta;
import com.latam.alura.foro.DAO.Respuesta.DatosRegistroRespuesta;
import com.latam.alura.foro.DAO.Respuesta.DatosRespuesta;
import com.latam.alura.foro.DAO.Usuario.DatosUsuario;
import com.latam.alura.foro.Domains.Respuesta;
import com.latam.alura.foro.Domains.Topico;
import com.latam.alura.foro.Domains.Usuario;
import com.latam.alura.foro.Exeptions.ResourceNotFoundException;
import com.latam.alura.foro.Exeptions.TopicoClosedException;
import com.latam.alura.foro.Repositories.RespuestaRepository;
import com.latam.alura.foro.Repositories.TopicoRepository;
import com.latam.alura.foro.Repositories.UsuarioRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    public RespuestaService(RespuestaRepository respuestaRepository, TopicoRepository topicoRepository,
                            UsuarioRepository usuarioRepository){
        this.respuestaRepository = respuestaRepository;
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;}

//CREATE Respuesta
    public DatosRespuesta registrarRespuesta(DatosRegistroRespuesta datosRegistro, Authentication authentication){
        Usuario autor = this.usuarioRepository.getReferenceById(datosRegistro.id_autor());
        Topico topico = this.topicoRepository.getReferenceById(datosRegistro.id_topico());

        validarAuthenticationUsuario(datosRegistro.id_autor(), authentication, "No Tiene" +
                "Permiso para Responder");

        validaEstadoTopico(topico);
        Respuesta respuesta = this.respuestaRepository.save(new Respuesta(datosRegistro, autor,topico));
        topico.setEstadoNoSolucionado();

        return factoryDatosRespuestaSA(respuesta);

    }

//READ GETByID Respuesta
    public DatosRespuesta buscarRespuesta(Long id){
        Respuesta respuesta = this.respuestaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Respuesta Solicitada No Existe"));
        return factoryDatosRespuesta(respuesta);
    }

//UPDATE Respuesta
    public DatosRespuesta actualizarRespuesta(DatosActualizarRespuesta datosActualizar, Authentication authentication){
        Respuesta respuesta = this.respuestaRepository.getReferenceById(datosActualizar.id());

        respuesta.actualizaRespuesta(datosActualizar);

        return factoryDatosRespuestaSA(respuesta);
    }

//DELETE Respuesta
    public void borrarRespuesta(Long id, Authentication authentication){
        Respuesta respuesta = this.respuestaRepository.getReferenceById(id);
        validarAuthenticationUsuario(respuesta.getAutor().getId(), authentication, "No Tiene" +
                "Permiso para Eliminar la Respuesta");
        this.respuestaRepository.delete(respuesta);
    }


    private DatosRespuesta factoryDatosRespuesta(Respuesta respuesta){
        return new DatosRespuesta(respuesta.getId(), respuesta.getMensaje(),
                respuesta.getFechaCreacion().toString(),
                new DatosUsuario(respuesta.getAutor().getId(), respuesta.getAutor().getNombre(),
                respuesta.getAutor().getEmail()), respuesta.getSolucion(), respuesta.getTopico().getId());
    }

    private DatosRespuesta factoryDatosRespuestaSA(Respuesta respuesta) {
        return new DatosRespuesta(respuesta.getId(), respuesta.getMensaje(),
                respuesta.getFechaCreacion().toString(),
                null, respuesta.getSolucion(), respuesta.getTopico().getId());
    }

    private void validaEstadoTopico(Topico topico) {
        if (topico.cerrado()){
            throw new TopicoClosedException("El Topico esta Cerrado y No Acepta mas Respuestas");
        }
    }

    private void validarAuthenticationUsuario(Long autorID,Authentication authentication, String mensaje){
        Usuario authenticateUser = (Usuario) authentication.getPrincipal();
        if (!autorID.equals(authenticateUser.getId())){
            throw new BadCredentialsException(mensaje);
        }
    }
}
