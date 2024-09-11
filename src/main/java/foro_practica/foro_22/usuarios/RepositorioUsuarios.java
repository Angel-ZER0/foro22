package foro_practica.foro_22.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface RepositorioUsuarios extends JpaRepository<Usuarios, Long>{
	
	UserDetails findByNombreUsuario(String username);
	
	@Query("select u from Usuarios u where u.nombreUsuario = :nombreUsuario")
	Usuarios encontrarUcuario(String nombreUsuario);
	
}
