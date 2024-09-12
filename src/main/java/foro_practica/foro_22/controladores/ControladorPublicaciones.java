package foro_practica.foro_22.controladores;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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
import foro_practica.foro_22.modelos.DatosPublicacion;
import foro_practica.foro_22.modelos.EstadoPublicacion;
import foro_practica.foro_22.modelos.InicioPrincipal;
import foro_practica.foro_22.modelos.NuevaPublicacion;
import foro_practica.foro_22.modelos.PublicacionSeleccionada;
import foro_practica.foro_22.repositorios.RepositorioInicioPrincipal;
import foro_practica.foro_22.servicioToken.ServicioToken;
import foro_practica.foro_22.usuarios.RepositorioUsuarios;
import foro_practica.foro_22.usuarios.Roles;
import foro_practica.foro_22.usuarios.Usuarios;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping({"/inicio", "/"})
public class ControladorPublicaciones {
	
	@Autowired
	private RepositorioUsuarios repoUsuario;
	@Autowired
	private RepositorioInicioPrincipal repoPublicacion;
	@Autowired
	private ServicioToken servicioToken;
	
	@PostMapping
	public ResponseEntity<DatosPublicacion> crearPublicacion(@RequestBody @Valid NuevaPublicacion nuevaPublicacion, UriComponentsBuilder uriComponentsBuilder) {
		
		Usuarios usuario = (Usuarios) repoUsuario.findByNombreUsuario(servicioToken.getNombreUsuario(servicioToken.conseguirToken()));
		InicioPrincipal crearPublicacion = repoPublicacion.save(new InicioPrincipal(nuevaPublicacion, usuario));
		DatosPublicacion nuevaPublicacionCreada = new DatosPublicacion(crearPublicacion);
		URI url = uriComponentsBuilder.path("/inicio/{id}").buildAndExpand(crearPublicacion.getId()).toUri();
		return ResponseEntity.created(url).body(nuevaPublicacionCreada);
		
	}
	
	@GetMapping
	public ResponseEntity<Page<DatosPublicacion>> listarPublicaciones(Pageable paginacion) {
		return ResponseEntity.ok(repoPublicacion.listarPublicaciones(paginacion).map(DatosPublicacion::new));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PublicacionSeleccionada> publicacionPorId(@PathVariable Long id) {
		
		InicioPrincipal publicacion = repoPublicacion.getReferenceById(id);
		if (publicacion.getEstado() != EstadoPublicacion.OCULTO) {
			PublicacionSeleccionada publicacionSeleccionada = new PublicacionSeleccionada(publicacion);
			return ResponseEntity.ok(publicacionSeleccionada);
		} else {
		
			return ResponseEntity.notFound().build();
			
		}
		
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<DatosPublicacion> actualizarPublicacion(@RequestBody @Valid ActualizarPublicacion publicacionAActualizar) {
		
		//Usuarios usuario = repoUsuario.encontrarUcuario(servicioToken.getNombreUsuario(servicioToken.conseguirToken()));
		Usuarios usuario = repoUsuario.getReferenceById(servicioToken.getIdUsuario(servicioToken.conseguirToken()));
		InicioPrincipal publicacion = repoPublicacion.getReferenceById(publicacionAActualizar.id());
		publicacion.actualizarPublicacionPorId(publicacionAActualizar, usuario);
		return ResponseEntity.ok(new DatosPublicacion(publicacion));
		
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity eliminarPublicacion (@PathVariable Long id) {
		
		repoPublicacion.eliminarPublicacionorId(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@DeleteMapping("/ocultar-publicacion/{id}")
	@Transactional
	public ResponseEntity ocultarPublicaion (@PathVariable Long id) {
		
		InicioPrincipal publicacion = repoPublicacion.getReferenceById(id);
		publicacion.ocultarPublicacion();
		return ResponseEntity.noContent().build();
			
	}
	
	@PatchMapping("/reponer-publicacion/{id}")
	@Transactional
	public ResponseEntity<DatosPublicacion> reponerPubilcacion(@RequestBody @PathVariable Long id) {
		
		InicioPrincipal publicacion = repoPublicacion.getReferenceById(id);
		publicacion.reponerPublicacion();
		return ResponseEntity.ok(new DatosPublicacion(publicacion)); 
		
	}
	
	@GetMapping("/{idUsuario}/mis-publicaciones")
	public ResponseEntity<Page<DatosPublicacion>> publicacionesPorUsuario(@PathVariable Long idUsuario, Pageable paginacion) {
		
		if (servicioToken.getIdUsuario(servicioToken.conseguirToken()) == idUsuario) {
			
			return ResponseEntity.ok(repoPublicacion.publicacionesUsuario(idUsuario, paginacion).map(DatosPublicacion::new));
			
		} else {
		
			return ResponseEntity.notFound().build();
			
		}
		
	}
	
}
