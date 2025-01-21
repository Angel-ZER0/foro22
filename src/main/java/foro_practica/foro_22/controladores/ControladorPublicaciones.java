package foro_practica.foro_22.controladores;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import foro_practica.foro_22.modelos.ActualizarPublicacion;
import foro_practica.foro_22.modelos.ContenidoPublicacion;
import foro_practica.foro_22.modelos.DatosPublicacion;
import foro_practica.foro_22.modelos.NuevaPublicacion;
import foro_practica.foro_22.modelos.PublicacionRepuesta;
import foro_practica.foro_22.servicios.ImpServInicioPrincipal;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/", "/inicio"})
public class ControladorPublicaciones {

	private final ImpServInicioPrincipal servInicioPrincipal;
	
	@PostMapping
	public ResponseEntity<DatosPublicacion> crearPublicacion(@RequestBody @Valid NuevaPublicacion nuevaPublicacion, UriComponentsBuilder uriComponentsBuilder) {
		
		return servInicioPrincipal.crearPublicacion(nuevaPublicacion);
		
	}
	
	@GetMapping 
	public ResponseEntity<Page<ContenidoPublicacion>> listarPublicaciones(Pageable paginacion) {
		
		return servInicioPrincipal.listarTodasLaspublicacionesPorFecha(paginacion);
		
	}
	
	@GetMapping("/{idPublicacion}")
	public ResponseEntity publicacionPorId(@PathVariable Long idPublicacion) {
		
		return servInicioPrincipal.detallesPublicacion(idPublicacion);
		
	}
	
	@PutMapping("/{idPublicacion}")
	@Transactional
	public ResponseEntity editarPublicacion (@PathVariable Long idPublicacion, @RequestBody ActualizarPublicacion publicacionAActualizar) {
		
		return servInicioPrincipal.editarPublicacion(idPublicacion, publicacionAActualizar);
		
	}
	
	@DeleteMapping("/{idPublicacion}")
	public ResponseEntity eliminarPublicacionUsuario (@PathVariable Long idPublicacion) {
		
		return servInicioPrincipal.eliminarPublicacionUsuario(idPublicacion);
		
	}
	
	@DeleteMapping("/ocultar-publicacion/{idPublicacion}")
	@Transactional
	public ResponseEntity ocultarPublicaion (@PathVariable Long idPublicacion) {
		
		return servInicioPrincipal.ocultarPublicacion(idPublicacion);
			
	}
	
	@PatchMapping("/reponer-publicacion/{id}")
	@Transactional
	public ResponseEntity<ContenidoPublicacion> reponerPubilcacion(@RequestBody PublicacionRepuesta publicacionRepuesta, @PathVariable Long id) {
		
		return servInicioPrincipal.reponerPubilcacion(publicacionRepuesta, id);
		
	}
	
	@GetMapping("/mis-publicaciones")
	public ResponseEntity publicacionesUsuario(Pageable paginacion) {
		
		return servInicioPrincipal.publicacionesUsuario(paginacion);
		
	}
	
}
