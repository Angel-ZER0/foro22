package foro_practica.foro_22.configuraciones;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioEncriptarContrasena {
	
	private final PasswordEncoder passwordEncoder;
	
	public String encriptacionContrasena(String contrasena) {
		
		return passwordEncoder.encode(contrasena);
		
	}

}
