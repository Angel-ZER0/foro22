package foro_practica.foro_22.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foro_practica.foro_22.servicioToken.ServicioToken;
import foro_practica.foro_22.usuarios.ServSelecUsuario;
import foro_practica.foro_22.usuarios.Usuarios;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/prueba")
@RequiredArgsConstructor
public class ControladorPrueba {
	
	private final ServSelecUsuario servSeleccionUsuario;
	private final ServicioToken servicioToken;

	@GetMapping
	public ResponseEntity <String> prueba () {
		
		Usuarios usuario = servSeleccionUsuario.seleccionarUsuario();
		
		if (usuario != null) {
		
		return ResponseEntity.ok("Ha accedido el usuario: " + usuario.getNombreUsuario() + 
				"\nCon el rol: " + usuario.getRol().toString() + 
				"\nEl token es: " + servicioToken.validarToken(servicioToken.conseguirToken()));
		
		} else {
			
			return ResponseEntity.status(403).body("algo pasó");
			
		}
		
	}
	
}
