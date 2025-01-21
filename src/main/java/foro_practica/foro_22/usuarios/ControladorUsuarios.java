package foro_practica.foro_22.usuarios;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foro_practica.foro_22.modelos.CorfirmacionEliminacionUsuario;
import foro_practica.foro_22.servicioToken.TokenGenerado;
import foro_practica.foro_22.servicios.ImpServUsuarios;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class ControladorUsuarios {

	private final ImpServUsuarios servUsuarios;
	
	@PostMapping("/registrar")
	public ResponseEntity registrarUsuario (@RequestBody @Valid RegistrarUsuario registrarNuevoUsuario) {
		
		return servUsuarios.registroNuevoUsuario(registrarNuevoUsuario);
		
	}
	
	@PostMapping("/accesar")
	public ResponseEntity<TokenGenerado> tokenAcceso (@RequestBody @Valid AccesoUsuario accesoUsuario) {
		
		return servUsuarios.accesoUsuario(accesoUsuario);
		
	}
	
	@Transactional
	@PutMapping("/editar-informacion")
	public ResponseEntity actualizarUsuario(@RequestBody @Valid ActualizarUsuario actualizarUsuario) {
		
		return servUsuarios.actualizarUsuario(actualizarUsuario);
		
	}
	
	@Transactional
	@DeleteMapping("/eliminar")
	public ResponseEntity eliminarUsuario(@RequestBody @Valid CorfirmacionEliminacionUsuario confirmacionEliminacionUsuario) {
		
		return servUsuarios.eliminarUsuario(confirmacionEliminacionUsuario);
		
	}
	
	@PostMapping("/registrar-administrador")
	public ResponseEntity registrarAdministrador (@RequestBody @Valid RegistrarUsuario registrarNuevoUsuario) {
		
		return servUsuarios.registroNuevoUsuario(registrarNuevoUsuario);
		
	}
	
}