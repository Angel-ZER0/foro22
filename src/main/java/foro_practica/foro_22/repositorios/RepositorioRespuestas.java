package foro_practica.foro_22.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import foro_practica.foro_22.modelos.RespuestasPublicaciones;
import jakarta.transaction.Transactional;

@Repository
public interface RepositorioRespuestas extends JpaRepository <RespuestasPublicaciones, Long> {
	
	/*
	@Query("""
			select respuestas, idUsuarioPublicacion
			from InicioPrincipal ipr 
			join RespuestasPublicaciones r on ipr.id = r.publicacion
			join Usuarios u on r.idUsuarioRespuesta = u.id
			where ipr.id = :id order by r.id desc
				""")
	List <RespuestasPublicaciones> listaRespuestas (@Param("id") Long id);
	
	
	@Query("""
		select respuesta from RespuestasPublicaciones rp  
		join InicioPrincipal ip on ip.id = rp.publicacion where rp.publicacion = 1   
	""")

	@Query("""
		select r.respuesta, r.publicacion, r.idUsuarioRespuesta from RespuestasPublicaciones r 
		join InicioPrincipal ip on ip.id = r.publicacion
		join Usuarios u on u.id = r.idUsuarioRespuesta
		where ip.id = :id
	""")
	*/
	
	@Query("""
		SELECT rp FROM RespuestasPublicaciones rp
		JOIN rp.publicacion ip
		JOIN rp.idUsuarioRespuesta u
		WHERE ip.id = :id
		ORDER BY rp.id ASC
	""")
	Page <RespuestasPublicaciones> respuestasPublicaciones (@Param("id") Long id, Pageable paginacion);
	
	@Query("delete from RespuestasPublicaciones rp where rp.id = :idRespuesta")
	@Transactional
	@Modifying
	void eliminarRespuesta(@Param("idRespuesta") Long id);
	
}
