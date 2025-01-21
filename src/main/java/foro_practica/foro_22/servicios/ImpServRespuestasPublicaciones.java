package foro_practica.foro_22.servicios;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import foro_practica.foro_22.modelos.ComentarioPublicacion;
import foro_practica.foro_22.modelos.EditarComentario;
import foro_practica.foro_22.modelos.EstadoPublicacion;
import foro_practica.foro_22.modelos.InicioPrincipal;
import foro_practica.foro_22.modelos.RespuestasPublicaciones;
import foro_practica.foro_22.modelos.RetornoRespuesta;
import foro_practica.foro_22.repositorios.RepositorioInicioPrincipal;
import foro_practica.foro_22.repositorios.RepositorioRespuestas;
import foro_practica.foro_22.servicioToken.ServicioToken;
import foro_practica.foro_22.usuarios.RepositorioUsuarios;
import foro_practica.foro_22.usuarios.ServSelecUsuario;
import foro_practica.foro_22.usuarios.Usuarios;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImpServRespuestasPublicaciones implements IntServRespuestasPublicaciones {

	private final RepositorioRespuestas repoRespuestas;
	private final ServSelecUsuario servSelecUsuario;
	private final RepositorioInicioPrincipal repoPublicacion;

	@Override
	public ResponseEntity publicarComentario(Long idPublicacion, ComentarioPublicacion comentario) {
		
		InicioPrincipal publicacion = repoPublicacion
				.publicacionAbierta(idPublicacion).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publicación no encontrada"));
		Usuarios usuario = servSelecUsuario.seleccionarUsuario();
		RespuestasPublicaciones respuesta = repoRespuestas.save(new RespuestasPublicaciones(comentario, usuario, publicacion));
		return ResponseEntity.ok(new RetornoRespuesta(respuesta));
		
	}

	@Override
	public ResponseEntity eliminarRespuesta(Long idPublicacion, Long idRespuesta) {
		
		Usuarios usuario = servSelecUsuario.seleccionarUsuario();
		InicioPrincipal publicacion = repoPublicacion.publicacionAbierta(idPublicacion)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publicación no encontrada"));
		
		if (!repoRespuestas.existsByIdAndPublicacionAndIdUsuarioRespuesta(idRespuesta, publicacion, usuario)) {
			
			return ResponseEntity.status(HttpStatus.CONFLICT).body("No puedes realizar esa acción");
		
			
		} 
		
		repoRespuestas.eliminarRespuesta(idRespuesta);
		return ResponseEntity.noContent().build();
		
	}

	@Override
	public ResponseEntity <Page<RetornoRespuesta>> listarRespuestasDePublicacion(Long idPublicaicon, Pageable paginacion) {
		
		InicioPrincipal publicacion = repoPublicacion.publicacionVisible(idPublicaicon).
				orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publicación no encontrada."));
		
		
		return ResponseEntity.ok(repoRespuestas.listarRespuestaPublicacionesVisibles(publicacion, paginacion).map(RetornoRespuesta::new));
		
	}

	@Override
	public ResponseEntity <RetornoRespuesta> editarRespuesta(Long idPublicacion, Long idRespuesta, EditarComentario edicionComentario) {
		
		Usuarios usuario = servSelecUsuario.seleccionarUsuario();
		InicioPrincipal publicacion = repoPublicacion.publicacionAbierta(idPublicacion).
				orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "La publicación, no existe ó está cerrada a comentarios / bajo revisión"));
		RespuestasPublicaciones respuesta = repoRespuestas.encontrarRespuestaUsuario(idRespuesta, usuario)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentario no encotrado."));
		respuesta.editarRespuesta(edicionComentario);
	
		
		
		return ResponseEntity.ok(new RetornoRespuesta(respuesta));
		 
		
	}

}
