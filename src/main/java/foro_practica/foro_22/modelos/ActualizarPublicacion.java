package foro_practica.foro_22.modelos;

public record ActualizarPublicacion(
	Long id,
	String titulo,
	String contenido,
	EstadoPublicacion estado
		) {

}
