package foro_practica.foro_22.modelos;

public record InsercionRespuestas(
		String respuesta,
		Long idPublicacion,
		Long idUsuario,
		String fecha
	) {

}
