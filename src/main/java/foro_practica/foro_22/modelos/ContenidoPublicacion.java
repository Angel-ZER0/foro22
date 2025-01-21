package foro_practica.foro_22.modelos;

public record ContenidoPublicacion(
		Long id,
		String titulo, 
		String contenido, 
		String fecha, 
		String estadoPublicacion,
		String autorPublicacion) {
	
	public ContenidoPublicacion(InicioPrincipal publicacion) {
		
		this(
			publicacion.getId(),
			publicacion.getTitulo(),
			publicacion.getContenido(),
			publicacion.getFecha().toString().replace("T", " Hora: ").substring(0, 22)/*.substring(0, publicacion.getFecha().toString().length() - 7).replace("T", " ")*/,
			publicacion.getEstado().toString(),
			publicacion.getIdUsuarioPublicacion().getNombreUsuario()
				);
		
	}

}
