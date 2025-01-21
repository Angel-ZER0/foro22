package foro_practica.foro_22.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;

import foro_practica.foro_22.modelos.ComentarioPublicacion;
import foro_practica.foro_22.modelos.EditarComentario;
import foro_practica.foro_22.modelos.InicioPrincipal;
import foro_practica.foro_22.modelos.RespuestasPublicaciones;
import foro_practica.foro_22.modelos.RetornoRespuesta;
import foro_practica.foro_22.usuarios.Usuarios;
import jakarta.validation.Valid;

public interface IntServRespuestasPublicaciones {

	public ResponseEntity publicarComentario(Long idPublicacion, ComentarioPublicacion comentario);
	public ResponseEntity eliminarRespuesta(Long idPublicacion, Long idRespuesta);
	public ResponseEntity <Page<RetornoRespuesta>> listarRespuestasDePublicacion(Long idRespuesta, Pageable paginacion);
	public ResponseEntity <RetornoRespuesta> editarRespuesta(Long idPublicacion, Long idRespuesta, EditarComentario edicionComentario);
}
