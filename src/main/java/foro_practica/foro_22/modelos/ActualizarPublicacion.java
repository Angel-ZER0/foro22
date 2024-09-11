package foro_practica.foro_22.modelos;

import jakarta.validation.constraints.NotNull;

public record ActualizarPublicacion(
	@NotNull Long id,
	String titulo,
	String contenido,
	EstadoPublicacion estado
		) {

}
