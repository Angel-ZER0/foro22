package foro_practica.foro_22.repositorios;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import foro_practica.foro_22.modelos.EstadoPublicacion;
import foro_practica.foro_22.modelos.InicioPrincipal;
import foro_practica.foro_22.usuarios.Usuarios;
import jakarta.transaction.Transactional;

@Repository
public interface RepositorioInicioPrincipal extends JpaRepository <InicioPrincipal, Long>{

	@Query("select p from InicioPrincipal p where estado != OCULTO")
	Page <InicioPrincipal> listarPublicaciones(Pageable paginacion);

	@Query("DELETE FROM InicioPrincipal p WHERE p.id = :idPublicacion")
	@Transactional
	@Modifying
	void eliminarPublicacionUsuario(Long idPublicacion);
	
	/*
	@Query("""
			select r from RespuestasPublicaciones rp
			join rp.publicacion r where rp.publicacion = 1   
			""")
	Page <RespuestasPublicaciones> respuestaPublicacion1 (Pageable paginacion);
	*/
	/*
	@Query("""
			select r from InicioPrincipal ip  
			join ip.respuestas r where ip.id = 1   
			""")
	*/
	
	@Query("SELECT p FROM InicioPrincipal p WHERE p.idUsuarioPublicacion = :usuario ORDER BY p.fecha DESC")
	Page <InicioPrincipal> publicacionesUsuario(@Param("usuario") Usuarios usuario, Pageable paginacion);
	
	@Query("SELECT p FROM InicioPrincipal p WHERE p.estado != OCULTO ORDER BY p.fecha DESC")
	Page <InicioPrincipal> listarPublicacionesFechaExcluirOcultos(Pageable paginacion);
	
	@Query("SELECT p FROM InicioPrincipal p WHERE p.id = :idPublicacion AND p.estado = :estado")
    Optional <InicioPrincipal> detallesPublicacion(@Param("idPublicacion") Long idPublicacion,
    		@Param("estado") EstadoPublicacion estado);
	
	@Query("SELECT p FROM InicioPrincipal p WHERE p.id = :idPublicacion AND p.estado = ABIERTO")
	Optional <InicioPrincipal> publicacionAbierta(Long idPublicacion);
	
	@Query("SELECT p FROM InicioPrincipal p WHERE p.id = :idPublicacion AND p.estado != OCULTO")
	Optional <InicioPrincipal> publicacionVisible(Long idPublicacion);
	
	@Query("""
			SELECT p FROM InicioPrincipal p
			WHERE p.id = :idPublicacion
			AND p.idUsuarioPublicacion = :usuario
			AND p.estado != OCULTO
		""")
	Optional <InicioPrincipal> seleccionarPublicacionUsuario(@Param("idPublicacion") Long idPublicacion, @Param("usuario") Usuarios usuario);
	
	@Query("DELETE FROM InicioPrincipal p WHERE p.idUsuarioPublicacion = :usuario")
	@Transactional
	@Modifying
	void eliminarPublicacionesUsuario(Usuarios usuario);
	
}
