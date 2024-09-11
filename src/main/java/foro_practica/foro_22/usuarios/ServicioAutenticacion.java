package foro_practica.foro_22.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ServicioAutenticacion implements UserDetailsService {

	@Autowired
	private RepositorioUsuarios repoUsuarios;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
	
		return repoUsuarios.findByNombreUsuario(username);
		
	}
	
	
	
}
