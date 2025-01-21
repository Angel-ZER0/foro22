package foro_practica.foro_22.usuarios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.transaction.Transactional;

public interface RepositorioUsuarios extends JpaRepository<Usuarios, Long>{
	
	Optional <UserDetails> findByNombreUsuario(String username);
	
	@Query("select u from Usuarios u where u.nombreUsuario = :nombreUsuario")
	Optional <Usuarios> encontrarUsuario(String nombreUsuario);
	
	@Query("""
			SELECT u FROM Usuarios u WHERE 
			u.nombreUsuario = :nombreUsuario OR
			u.correo = :correo
			""")
	List <Usuarios> findByNombreUsuarioOrCorreo(@Param("nombreUsuario") String nombreUsuario, 
			@Param("correo") String correo);
	
	@Query("DELETE FROM Usuarios u WHERE u.id = :idUsuario")
	@Transactional
	@Modifying
	void eliminarUsuarioPorId(Long idUsuario);
	
}
