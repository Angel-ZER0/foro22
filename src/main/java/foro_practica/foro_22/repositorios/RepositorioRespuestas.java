package foro_practica.foro_22.repositorios;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import foro_practica.foro_22.modelos.InicioPrincipal;
import foro_practica.foro_22.modelos.RespuestasPublicaciones;
import foro_practica.foro_22.usuarios.Usuarios;
import jakarta.transaction.Transactional;

@Repository
public interface RepositorioRespuestas extends JpaRepository <RespuestasPublicaciones, Long> {
	
	@Query("""
		SELECT rp FROM RespuestasPublicaciones rp
		JOIN rp.publicacion ip
		JOIN rp.idUsuarioRespuesta u
		WHERE ip.id = :id
		ORDER BY rp.id ASC
	""")
	Page <RespuestasPublicaciones> respuestasPublicaciones (@Param("id") Long id, Pageable paginacion);
	
	@Query("""
			SELECT rp FROM RespuestasPublicaciones rp
			WHERE rp.publicacion = :publicacion
			AND rp.publicacion.estado != OCULTO
			ORDER BY rp.fechaRespuesta ASC
			""")
	Page <RespuestasPublicaciones> listarRespuestaPublicacionesVisibles(@Param("publicacion") InicioPrincipal publicacion, Pageable paginacion);
	
	@Query("""
			SELECT rp FROM RespuestasPublicaciones rp
			WHERE rp.id = :idRespuesta 
			AND rp.idUsuarioRespuesta = :usuario
			""")
	Optional <RespuestasPublicaciones> encontrarRespuestaUsuario(@Param("idRespuesta") Long idRespuesta, 
			@Param("usuario") Usuarios usuario);
	
	boolean existsByIdAndPublicacionAndIdUsuarioRespuesta(Long id, InicioPrincipal publicacion, Usuarios idUsuarioRespuesta);
	
	@Query("delete from RespuestasPublicaciones rp where rp.id = :idRespuesta")
	@Transactional
	@Modifying
	void eliminarRespuesta(@Param("idRespuesta") Long id);
	
	@Query("DELETE from RespuestasPublicaciones rp WHERE rp.idUsuarioRespuesta = :usuario")
	@Transactional
	@Modifying
	void eliminarRespuestasUsuario(Usuarios usuario);
	
	
}
