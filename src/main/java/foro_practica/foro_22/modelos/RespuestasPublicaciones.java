package foro_practica.foro_22.modelos;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UserDetails;

import foro_practica.foro_22.usuarios.Usuarios;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "respuestas_publicaciones")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RespuestasPublicaciones {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String respuesta;
	@ManyToOne
	private InicioPrincipal publicacion;
	@ManyToOne
	private Usuarios idUsuarioRespuesta;
	private LocalDateTime fechaRespuesta;
	
	public RespuestasPublicaciones(ComentarioPublicacion comentario, Usuarios usuario, InicioPrincipal publicacion) {
		
		this.respuesta = comentario.respuesta();
		this.publicacion = publicacion;
		this.idUsuarioRespuesta = usuario;
		this.fechaRespuesta = LocalDateTime.now();
		
	}
	
	public void editarRespuesta(EditarComentario edicionComentario) {
		
		this.respuesta = edicionComentario.respuesta();
		this.fechaRespuesta =  LocalDateTime.now();
		
	}
	
}
