package foro_practica.foro_22.usuarios;

public record VerificarDuplicados(String nombreUsuario, String correo) {

	public VerificarDuplicados(Usuarios usuario) {
		this(usuario.getNombreUsuario(), usuario.getCorreo());
	}
	
}
