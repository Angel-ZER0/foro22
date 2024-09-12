package foro_practica.foro_22.configuraciones;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import foro_practica.foro_22.servicioToken.ServicioToken;
import foro_practica.foro_22.usuarios.RepositorioUsuarios;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroDeSeguridad extends OncePerRequestFilter {
	
	@Autowired
	private ServicioToken servicioToken;
	@Autowired
	private RepositorioUsuarios repoUsuarios;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if (authHeader != null) {
			
			String token = authHeader.replace("Bearer ", "");
			var nombreUsuario = servicioToken.getNombreUsuario(token);
			
			if (nombreUsuario != null) {
				
				var usuario = repoUsuarios.findByNombreUsuario(nombreUsuario);
				var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}
								
		}
		
		filterChain.doFilter(request, response);
		
	}

}
