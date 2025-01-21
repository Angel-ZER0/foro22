package foro_practica.foro_22.controladores;

import org.springframework.data.domain.Page;
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

import foro_practica.foro_22.modelos.ComentarioPublicacion;
import foro_practica.foro_22.modelos.ContenidoPublicacion;
import foro_practica.foro_22.modelos.EditarComentario;
import foro_practica.foro_22.modelos.InicioPrincipal;
import foro_practica.foro_22.modelos.RespuestasPublicaciones;
import foro_practica.foro_22.modelos.RetornoRespuesta;
import foro_practica.foro_22.repositorios.RepositorioInicioPrincipal;
import foro_practica.foro_22.repositorios.RepositorioRespuestas;
import foro_practica.foro_22.servicioToken.ServicioToken;
import foro_practica.foro_22.servicios.IntServRespuestasPublicaciones;
import foro_practica.foro_22.usuarios.RepositorioUsuarios;
import foro_practica.foro_22.usuarios.Usuarios;
import jakarta.annotation.security.PermitAll;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/{idPublicacion}", "/inicio/{idPublicacion}"})
public class ControladorRespuestas {

	private final IntServRespuestasPublicaciones servicioRespuestas;
	
	@PostMapping
	public ResponseEntity publicarRespuesta (@RequestBody @Valid ComentarioPublicacion comentario, @PathVariable Long idPublicacion) {
		
		return servicioRespuestas.publicarComentario(idPublicacion, comentario);
		
	}

	@GetMapping("/respuestas")
	public ResponseEntity <Page<RetornoRespuesta>> listarRespuestasDePublicacion (@PathVariable Long idPublicacion, Pageable paginacion) {
		
		return servicioRespuestas.listarRespuestasDePublicacion(idPublicacion, paginacion);
		
	}

	@DeleteMapping("/respuestas/{idRespuesta}")
	public ResponseEntity eliminarComentario(@PathVariable Long idPublicacion, @PathVariable Long idRespuesta) {
		
		return servicioRespuestas.eliminarRespuesta(idPublicacion, idRespuesta);

	}
	
	@Transactional
	@PutMapping("/respuestas/{idRespuesta}")
	public ResponseEntity <RetornoRespuesta> actualizarRespuesta(@PathVariable Long idPublicacion, @PathVariable Long idRespuesta, @RequestBody @Valid EditarComentario edicionComentario) {
		
		return servicioRespuestas.editarRespuesta(idPublicacion, idRespuesta, edicionComentario);
		
	}
	
}
