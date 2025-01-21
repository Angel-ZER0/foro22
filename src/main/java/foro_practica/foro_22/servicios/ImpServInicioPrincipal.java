package foro_practica.foro_22.servicios;

import java.net.URI;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import foro_practica.foro_22.modelos.ActualizarPublicacion;
import foro_practica.foro_22.modelos.ContenidoPublicacion;
import foro_practica.foro_22.modelos.DatosPublicacion;
import foro_practica.foro_22.modelos.EstadoPublicacion;
import foro_practica.foro_22.modelos.InicioPrincipal;
import foro_practica.foro_22.modelos.NuevaPublicacion;
import foro_practica.foro_22.modelos.PublicacionRepuesta;
import foro_practica.foro_22.repositorios.RepositorioInicioPrincipal;
import foro_practica.foro_22.usuarios.ServSelecUsuario;
import foro_practica.foro_22.usuarios.Usuarios;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImpServInicioPrincipal implements IntServInicioPrincipal{
	
	private final RepositorioInicioPrincipal repoPublicacion;
	private final ServSelecUsuario servSelecUsuario;
	private final UriComponentsBuilder uriComponentsBuilder;

	@Override
	public ResponseEntity <Page<ContenidoPublicacion>> listarTodasLaspublicacionesPorFecha(Pageable paginacion) {
		
		return ResponseEntity.ok(repoPublicacion.listarPublicacionesFechaExcluirOcultos(paginacion).map(ContenidoPublicacion::new));
		
	}

	@Override
	public ResponseEntity <DatosPublicacion> crearPublicacion(NuevaPublicacion nuevaPublicacion) {
		
		Usuarios usuario = servSelecUsuario.seleccionarUsuario();
		InicioPrincipal publicacion = repoPublicacion.save(new InicioPrincipal(nuevaPublicacion, usuario));
		DatosPublicacion contenidoPublicacion = new DatosPublicacion(publicacion);
		URI url = uriComponentsBuilder.path("/inicio/{id}").buildAndExpand(contenidoPublicacion.id()).toUri();
		return ResponseEntity.created(url).body(contenidoPublicacion);
	}

	@Override
	public ResponseEntity publicacionesUsuario(Pageable paginacion) {
		
		Usuarios usuario = servSelecUsuario.seleccionarUsuario();
		
		if (usuario == null) {
			
			return ResponseEntity.badRequest().body("No estás registrado para visualizar esta sección.");
		}
			
		return ResponseEntity.ok(repoPublicacion.publicacionesUsuario(usuario, paginacion).map(ContenidoPublicacion::new));
			
	}

	@Override
	public ResponseEntity detallesPublicacion(Long idPublicacion) {

		Optional <InicioPrincipal> publicacion = repoPublicacion.detallesPublicacion(idPublicacion, EstadoPublicacion.ABIERTO);
		
		if (publicacion.isPresent()) {
			
			return ResponseEntity.ok(new ContenidoPublicacion(publicacion.get()));
			
		} else {
			
			return ResponseEntity.notFound().build();
			
		}
		
	}

	@Override
	public ResponseEntity editarPublicacion(Long idPublicacion, ActualizarPublicacion actualizarPublicacion) {

		Usuarios usuario = servSelecUsuario.seleccionarUsuario();
		Optional<InicioPrincipal> publicacion = repoPublicacion.seleccionarPublicacionUsuario(idPublicacion, usuario);

		if (publicacion.isPresent()) {

			publicacion.get().actualizarPublicacionPorId(actualizarPublicacion);
			return ResponseEntity.ok(new ContenidoPublicacion(publicacion.get()));

		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Publicacion no encontrada o bajo revisión");

		}

	}

	@Override
	public ResponseEntity eliminarPublicacionUsuario(Long idPublicacion) {
		
		Usuarios usuario = servSelecUsuario.seleccionarUsuario();
		Optional <InicioPrincipal> publicacion = repoPublicacion.seleccionarPublicacionUsuario(idPublicacion, usuario);
		
		if (publicacion.isPresent()) {
			
			repoPublicacion.eliminarPublicacionUsuario(publicacion.get().getId());
			return ResponseEntity.noContent().build();
			
		} else {
			
			return ResponseEntity.status(HttpStatus.CONFLICT).body("No has hecho tal publicación o dicha publicacion está en revisión");
			
		}
 		
	}

	@Override
	public ResponseEntity ocultarPublicacion(Long idPublicacion) {
		
		Optional<InicioPrincipal> publicacion = repoPublicacion.findById(idPublicacion);
		
		if (publicacion.isPresent()) {
			
			publicacion.get().ocultarPublicacion();
			return ResponseEntity.ok("La publicación se ha ocultado");
			
		} else {
			
			return ResponseEntity.notFound().build();
			
		}
		
	}

	@Override
	public ResponseEntity reponerPubilcacion(PublicacionRepuesta publicacionRepuesta, Long idPublicacion) {

		Optional<InicioPrincipal> publicacion = repoPublicacion.findById(idPublicacion);

		if (publicacion.isPresent()) {
			
			if (publicacionRepuesta.estadoPublicacion() == EstadoPublicacion.ABIERTO) {
				
				publicacion.get().abrirPublicacion();
				return ResponseEntity.ok("La publicación vuelve a ser visible con estado abierto.\n" + 
				new ContenidoPublicacion(publicacion.get()));
				
			} else if (publicacionRepuesta.estadoPublicacion() == EstadoPublicacion.CERRADO) {
				
				publicacion.get().cerrarPublicacion();
				return ResponseEntity.ok("La publicación vuelve a ser visible sin posibilidad a comentar más en ella.\n" + 
				new ContenidoPublicacion(publicacion.get()));
				
			} else {
				
				return ResponseEntity.ok("La publicación permanecerá oculta");
				
			}
			

		} else {

			return ResponseEntity.notFound().build();

		}

	}

}
