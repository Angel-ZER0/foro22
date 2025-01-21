package foro_practica.foro_22.servicios;

import org.springframework.http.ResponseEntity;

import foro_practica.foro_22.modelos.CorfirmacionEliminacionUsuario;
import foro_practica.foro_22.usuarios.AccesoUsuario;
import foro_practica.foro_22.usuarios.ActualizarUsuario;
import foro_practica.foro_22.usuarios.RegistrarUsuario;
import jakarta.transaction.Transactional;

public interface IntServUsuarios {

	ResponseEntity registroNuevoUsuario(RegistrarUsuario registrarNuevoUsuario);
	
	ResponseEntity accesoUsuario(AccesoUsuario credenciales);
	
	ResponseEntity actualizarUsuario(ActualizarUsuario actualizarUsuario);
	
	ResponseEntity eliminarUsuario(CorfirmacionEliminacionUsuario confirmacion);
	
}
