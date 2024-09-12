package foro_practica.foro_22.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import foro_practica.foro_22.modelos.InicioPrincipal;
import foro_practica.foro_22.modelos.PublicacionSeleccionada;
import foro_practica.foro_22.modelos.RespuestasPublicaciones;
import foro_practica.foro_22.usuarios.Usuarios;
import jakarta.transaction.Transactional;

@Repository
public interface RepositorioInicioPrincipal extends JpaRepository <InicioPrincipal, Long>{

	@Query("select p from InicioPrincipal p where estado != OCULTO")
	Page <InicioPrincipal> listarPublicaciones(Pageable paginacion);

	@Query("delete from InicioPrincipal p where p.id = :id")
	@Transactional
	@Modifying
	void eliminarPublicacionorId(@Param(value = "id") Long id);
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
	
	@Query("select p from InicioPrincipal p where p.idUsuarioPublicacion.id = :idUsuario")
	Page <InicioPrincipal> publicacionesUsuario(@Param("idUsuario") Long idUsuario, Pageable paginacion);
	
}
