package foro_practica.foro_22.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import foro_practica.foro_22.servicioToken.ServicioToken;
import foro_practica.foro_22.servicioToken.TokenGenerado;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class ControladorUsuarios {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private ServicioToken servicioToken;
	@Autowired
	private RepositorioUsuarios repoUsuarios;
	
	@PostMapping("/registrar")
	public ResponseEntity registrarUsuario (@RequestBody @Valid RegistrarUsuario registrarNuevoUsuario) {
		
		Usuarios nuevoUsuario = new Usuarios(registrarNuevoUsuario);
		repoUsuarios.save(nuevoUsuario);
		return ResponseEntity.ok("El usuario fue registrado exitósamente");
		
	}
	
	@PostMapping("/accesar")
	public ResponseEntity<TokenGenerado> tokenAcceso (@RequestBody @Valid AccesoUsuario accesoUsuario) {
		
		Authentication authToken = new UsernamePasswordAuthenticationToken(accesoUsuario.nombreUsuario(), accesoUsuario.contrasena());
		var autenticacionUsuario = authenticationManager.authenticate(authToken);
		String JWTToken = servicioToken.creadorToken((Usuarios) autenticacionUsuario.getPrincipal());
		return ResponseEntity.ok(new TokenGenerado(JWTToken));
		
	}
	
}