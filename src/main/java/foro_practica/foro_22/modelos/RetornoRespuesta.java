package foro_practica.foro_22.modelos;

public record RetornoRespuesta(Long id, String respuesta, String nombreUsuario, String fechaRespuesta) {

	public RetornoRespuesta(RespuestasPublicaciones respuesta) {
		this(respuesta.getId(), respuesta.getRespuesta(), respuesta.getIdUsuarioRespuesta().getNombreUsuario(), respuesta.getFechaRespuesta().toString().substring(0, respuesta.getFechaRespuesta().toString().length() - 7).replace("T", " "));
	}
	
}
