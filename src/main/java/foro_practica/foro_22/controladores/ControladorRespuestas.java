package foro_practica.foro_22.controladores;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import foro_practica.foro_22.interfasEImp.RespuestasPublicacionesService;
import foro_practica.foro_22.modelos.ComentarioPublicacion;
import foro_practica.foro_22.modelos.EditarComentario;
import foro_practica.foro_22.modelos.InicioPrincipal;
import foro_practica.foro_22.modelos.RespuestasPublicaciones;
import foro_practica.foro_22.modelos.RetornoRespuesta;
import foro_practica.foro_22.repositorios.RepositorioInicioPrincipal;
import foro_practica.foro_22.repositorios.RepositorioRespuestas;
import foro_practica.foro_22.servicioToken.ServicioToken;
import foro_practica.foro_22.usuarios.RepositorioUsuarios;
import foro_practica.foro_22.usuarios.Usuarios;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{id}")
public class ControladorRespuestas {
	
	private final RepositorioRespuestas repoRespuestas;
	private final RepositorioUsuarios repoUsuario;
	private final RepositorioInicioPrincipal repoPublicacion;
	private final ServicioToken servicioToken;
	private final RespuestasPublicacionesService servicioRespuestas;
	
	@PostMapping
	public ResponseEntity publicarRespuesta (@RequestBody @Valid ComentarioPublicacion comentario, @PathVariable Long id) {
		
		InicioPrincipal publicacion = repoPublicacion.getReferenceById(id);
		Usuarios usuario = repoUsuario.encontrarUcuario(servicioToken.getNombreUsuario(servicioToken.conseguirToken()));
		return servicioRespuestas.publicarComentario(comentario, publicacion, usuario);
		
	}
	
	@GetMapping("/respuestas")
	public ResponseEntity listarRespuestas (@PathVariable Long id, Pageable paginacion) {
		
		InicioPrincipal publicacion = repoPublicacion.getReferenceById(id);
		return servicioRespuestas.listarRespuestas(publicacion, paginacion);
		
	}
	
	@DeleteMapping("/respuestas/{idRespuesta}")
	public ResponseEntity eliminarComentario(@PathVariable Long id, @PathVariable Long idRespuesta) {
		
		InicioPrincipal publicacion = repoPublicacion.getReferenceById(id);
		Usuarios usuario = repoUsuario.encontrarUcuario(servicioToken.getNombreUsuario(servicioToken.conseguirToken()));
		RespuestasPublicaciones respuesta = repoRespuestas.getReferenceById(idRespuesta);
		return servicioRespuestas.eliminarRespuesta(publicacion, usuario, respuesta);

	}

	@Transactional
	@PutMapping("/respuestas")
	public ResponseEntity actualizarRespuesta(@RequestBody @Valid EditarComentario edicionComentario, @PathVariable Long id) {
		
		InicioPrincipal publicacion = repoPublicacion.getReferenceById(id);
		Usuarios usuario = repoUsuario.encontrarUcuario(servicioToken.getNombreUsuario(servicioToken.conseguirToken()));
		return servicioRespuestas.respuestaEditada(publicacion, edicionComentario, usuario);
		
	}
	
}
