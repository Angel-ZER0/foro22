package foro_practica.foro_22.usuarios;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ServicioAutenticacion implements UserDetailsService {
	
	private final RepositorioUsuarios repoUsuarios;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		return repoUsuarios.findByNombreUsuario(username)
				 .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + username));
		
	}
	
	
	
}