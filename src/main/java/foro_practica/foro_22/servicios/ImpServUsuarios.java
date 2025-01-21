package foro_practica.foro_22.servicios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import foro_practica.foro_22.configuraciones.ServicioEncriptarContrasena;
import foro_practica.foro_22.modelos.CorfirmacionEliminacionUsuario;
import foro_practica.foro_22.repositorios.RepositorioInicioPrincipal;
import foro_practica.foro_22.repositorios.RepositorioRespuestas;
import foro_practica.foro_22.servicioToken.ServicioToken;
import foro_practica.foro_22.servicioToken.TokenGenerado;
import foro_practica.foro_22.usuarios.AccesoUsuario;
import foro_practica.foro_22.usuarios.ActualizarUsuario;
import foro_practica.foro_22.usuarios.RegistrarUsuario;
import foro_practica.foro_22.usuarios.RepositorioUsuarios;
import foro_practica.foro_22.usuarios.Roles;
import foro_practica.foro_22.usuarios.ServSelecUsuario;
import foro_practica.foro_22.usuarios.Usuarios;
import foro_practica.foro_22.usuarios.VerificarDuplicados;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImpServUsuarios implements IntServUsuarios {

	private final AuthenticationManager authenticationManager;
	private final HttpServletRequest request;
	private final RepositorioUsuarios repoUsuarios;
	private final RepositorioRespuestas repoRespuestas;
	private final RepositorioInicioPrincipal repoPublicacion;
	private final ServicioToken servicioToken;
	private final ServSelecUsuario servSelecUsuario;
	private final ServicioEncriptarContrasena servEncriptarContrasena;
	
	
	@Override
	public ResponseEntity registroNuevoUsuario(RegistrarUsuario registrarNuevoUsuario) {

		String endpoindPeticion = request.getRequestURI();

		List<VerificarDuplicados> datosDuplicados = repoUsuarios
				.findByNombreUsuarioOrCorreo(registrarNuevoUsuario.nombreUsuario(), registrarNuevoUsuario.correo())
				.stream().map(VerificarDuplicados::new).collect(Collectors.toList());

		if (datosDuplicados.isEmpty()) {

			if (("/usuario/registrar").equals(endpoindPeticion)) {
 
				Usuarios nuevoUsuario = new Usuarios(registrarNuevoUsuario, 
						servEncriptarContrasena.encriptacionContrasena(registrarNuevoUsuario.contrasena()), 
						Roles.USER);
				repoUsuarios.save(nuevoUsuario);
				
				return ResponseEntity
						.ok("El usuario " + nuevoUsuario.getNombreUsuario() + " fue registrado exitósamente");

			} else if (("/usuario/registrar-administrador").equals(endpoindPeticion)) {

				Usuarios nuevoUsuario = new Usuarios(registrarNuevoUsuario, 
						servEncriptarContrasena.encriptacionContrasena(registrarNuevoUsuario.contrasena()), 
						Roles.ADMIN);
				repoUsuarios.save(nuevoUsuario);
				
				return ResponseEntity
						.ok("El administrador " + nuevoUsuario.getNombreUsuario() + " fue registrado exitósamente");

			} else {

				return ResponseEntity.badRequest().build();

			}

		} else {

			boolean nombreUsuarioDuplicado = datosDuplicados.stream().anyMatch(
					usuario -> usuario.nombreUsuario().equalsIgnoreCase(registrarNuevoUsuario.nombreUsuario()));
			boolean correoDuplicado = datosDuplicados.stream()
					.anyMatch(usuario -> usuario.correo().equalsIgnoreCase(registrarNuevoUsuario.correo()));

			if (nombreUsuarioDuplicado && correoDuplicado) {

				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body("El correo y nombre de usuario ya han sido registrados");

			} else if (correoDuplicado) {

				return ResponseEntity.status(409)
						.body("El correo: " + registrarNuevoUsuario.correo() + " ya ha sido regisstrado.");

			} else {

				return ResponseEntity.status(409)
						.body("El nombre de usuario: " + registrarNuevoUsuario.nombreUsuario() + " no está disponible");

			}

		}

	}

	@Override
	public ResponseEntity accesoUsuario(AccesoUsuario credenciales) {
		
		try {

			Authentication authToken = new UsernamePasswordAuthenticationToken(credenciales.nombreUsuario(),
					credenciales.contrasena());
			var autenticacionUsuario = authenticationManager.authenticate(authToken);
			String JWTToken = servicioToken.creadorToken((Usuarios) autenticacionUsuario.getPrincipal());
			return ResponseEntity.ok(new TokenGenerado(JWTToken));

		} catch (BadCredentialsException e) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Verifica tus datos de acceso.");

		}
		
	}

	@Override
	public ResponseEntity actualizarUsuario(ActualizarUsuario actualizarUsuario) {
		
		Usuarios usuario = servSelecUsuario.seleccionarUsuario();
		
		if (usuario != null) {
			
			if (repoUsuarios.encontrarUsuario(actualizarUsuario.nombreUsuario()).isPresent()) {

				return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya ha sido registrado este nombre de usuario");

			} else {
				
				usuario.actualizarUsuario(actualizarUsuario, actualizarUsuario.contrasena());
				return ResponseEntity.ok("Información actualizada, tendrás que volver a iniciar sesión con tus nuevas credenciales");
				
			}
			
		} else {
			
			return ResponseEntity.status(403).body("Usuario no autenticado");
			
		}

	}
		

	@Override
	public ResponseEntity eliminarUsuario(CorfirmacionEliminacionUsuario confirmacion) {
		
		Usuarios usuario = servSelecUsuario.seleccionarUsuario();
		
		if (confirmacion.confirmacion()) {
			
			repoRespuestas.eliminarRespuestasUsuario(usuario);
			repoPublicacion.eliminarPublicacionesUsuario(usuario);
			repoUsuarios.eliminarUsuarioPorId(usuario.getId());
			return ResponseEntity.ok("Tu usuario, publicaciones y respuestas han sido eliminadas");
			
		} 
		
		return ResponseEntity.ok("El usuario no ha sido eliminado.");
		
	}

}
