package foro_practica.foro_22.modelos;

public record DatosPublicacion(Long id, String titulo, String contenido, String estado, String fecha, String autorPublicacion) {

	public DatosPublicacion(InicioPrincipal publicacion) {
		this(
				publicacion.getId(), 
				publicacion.getTitulo(), 
				publicacion.getContenido(), 
				publicacion.getEstado().toString(), 
				publicacion.getFecha().toString().replace("T", " Hora: ").substring(0, 22), 
				publicacion.getIdUsuarioPublicacion().getNombreUsuario());
	}
	
}
