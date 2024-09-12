package foro_practica.foro_22.interfasEImp;

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

public interface RespuestasPublicacionesService {

	public ResponseEntity publicarComentario(ComentarioPublicacion comentario, InicioPrincipal publicacion, Usuarios usuario);
	public ResponseEntity eliminarRespuesta(InicioPrincipal publicacion, Usuarios usuario, RespuestasPublicaciones respuesta);
	public ResponseEntity listarRespuestas(InicioPrincipal publicacion, Pageable paginacion);
	public ResponseEntity respuestaEditada(InicioPrincipal publicacion, EditarComentario edicionComentario, Usuarios usuario);
}
