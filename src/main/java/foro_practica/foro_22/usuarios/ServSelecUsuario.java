package foro_practica.foro_22.usuarios;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServSelecUsuario {
	
	private final RepositorioUsuarios repoUsuarios;
	
	public Usuarios seleccionarUsuario() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth == null || !auth.isAuthenticated()) {
			
			throw new SecurityException("No se obtuvo la autenticación del contexto de seguridad");
			
		}
		
		String nombreUsuario = auth.getName();
		
		if (nombreUsuario == null || nombreUsuario.isBlank()) {
			
			throw new RuntimeException("Ocurrió un error al procesar la autenticación");
			
		}
			
		return repoUsuarios.encontrarUsuario(nombreUsuario).orElse(null);

	
	}
		
}
