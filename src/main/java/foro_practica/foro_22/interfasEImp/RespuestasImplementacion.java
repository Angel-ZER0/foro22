package foro_practica.foro_22.interfasEImp;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
import foro_practica.foro_22.usuarios.Usuarios;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RespuestasImplementacion implements RespuestasPublicacionesService {

	private final RepositorioRespuestas repoRespuestas;

	@Override
	public ResponseEntity publicarComentario(ComentarioPublicacion comentario, InicioPrincipal publicacion,
			Usuarios usuario) {
		
		if (publicacion.getEstado() != EstadoPublicacion.OCULTO && publicacion.getEstado() != EstadoPublicacion.CERRADO) {
			
			RespuestasPublicaciones comentarioPublicado = repoRespuestas
					.save(new RespuestasPublicaciones(comentario, usuario, publicacion));
			return ResponseEntity.ok(new RetornoRespuesta(comentarioPublicado));
			
		} else {
			
			return ResponseEntity.status(403).build();
			
		}

		
	}

	@Override
	public ResponseEntity listarRespuestas(InicioPrincipal publicacion, Pageable paginacion) {

		if (publicacion.getEstado() == EstadoPublicacion.OCULTO) {

			return ResponseEntity.notFound().build();

		} else {

			return ResponseEntity.ok(
					
					repoRespuestas.respuestasPublicaciones(publicacion.getId(), paginacion).map(RetornoRespuesta::new));

		}

	}

	@Override
	public ResponseEntity eliminarRespuesta(InicioPrincipal publicacion, Usuarios usuario,
			RespuestasPublicaciones respuesta) {

		if (publicacion == null || publicacion.getEstado() == EstadoPublicacion.OCULTO) {

			return ResponseEntity.notFound().build();

		} else if (respuesta != null && publicacion.getRespuestas().contains(respuesta)
				&& respuesta.getIdUsuarioRespuesta() == usuario && publicacion.getEstado() != EstadoPublicacion.OCULTO && publicacion.getEstado() != EstadoPublicacion.CERRADO) {

			repoRespuestas.eliminarRespuesta(respuesta.getId());
			return ResponseEntity.noContent().build();

		}

		return ResponseEntity.status(403).build();

	}

	@Override
	public ResponseEntity respuestaEditada(InicioPrincipal publicacion, EditarComentario edicionComentario,
			Usuarios usuario) {

		RespuestasPublicaciones respuesta = repoRespuestas.getReferenceById(edicionComentario.id());

		if (publicacion == null || publicacion.getEstado() == EstadoPublicacion.OCULTO) {

			return ResponseEntity.notFound().build();

		} else if (respuesta != null && publicacion.getRespuestas().contains(respuesta)
				&& respuesta.getIdUsuarioRespuesta() == usuario) {

			respuesta.editarRespuesta(edicionComentario);
			return ResponseEntity.ok(new RetornoRespuesta(respuesta));

		}

		return ResponseEntity.status(403).build();

	}

}
