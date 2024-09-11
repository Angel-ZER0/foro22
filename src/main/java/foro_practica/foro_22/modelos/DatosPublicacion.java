package foro_practica.foro_22.modelos;

public record DatosPublicacion(Long id, String titulo, String contenido, String estado, String fecha, String AutorPublicacion) {

	public DatosPublicacion(InicioPrincipal publicacion) {
		this(publicacion.getId(), publicacion.getTitulo(), publicacion.getContenido(), publicacion.getEstado().toString(), publicacion.getFecha().toString().substring(0, publicacion.getFecha().toString().length() - 7).replace("T", " "), publicacion.getIdUsuarioPublicacion().getNombreUsuario());
	}
	
}
