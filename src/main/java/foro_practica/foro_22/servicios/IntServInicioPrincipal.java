package foro_practica.foro_22.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import foro_practica.foro_22.modelos.ActualizarPublicacion;
import foro_practica.foro_22.modelos.ContenidoPublicacion;
import foro_practica.foro_22.modelos.DatosPublicacion;
import foro_practica.foro_22.modelos.NuevaPublicacion;
import foro_practica.foro_22.modelos.PublicacionRepuesta;

public interface IntServInicioPrincipal {

	ResponseEntity <Page<ContenidoPublicacion>> listarTodasLaspublicacionesPorFecha (Pageable paginacion);
	
	ResponseEntity <DatosPublicacion> crearPublicacion (NuevaPublicacion nuevaPublicacion);
	
	ResponseEntity <Page<ContenidoPublicacion>> publicacionesUsuario(Pageable paginacion);
	
	ResponseEntity detallesPublicacion(Long idPublicacion);
	
	ResponseEntity editarPublicacion(Long idPublicacion, ActualizarPublicacion actualizarPublicacion);
	
	ResponseEntity eliminarPublicacionUsuario(Long idPublicacion);
	
	ResponseEntity ocultarPublicacion(Long idPublicacion);
	
	ResponseEntity reponerPubilcacion(PublicacionRepuesta publicacionRepuesta, Long idPublicacion);
	
}