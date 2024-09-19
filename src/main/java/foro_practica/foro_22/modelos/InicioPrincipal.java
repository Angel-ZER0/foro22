package foro_practica.foro_22.modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import foro_practica.foro_22.usuarios.Roles;
import foro_practica.foro_22.usuarios.Usuarios;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inicio_principal")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InicioPrincipal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String contenido;
	private EstadoPublicacion estado;
	@ManyToOne
	private Usuarios idUsuarioPublicacion;
	private LocalDateTime fecha;
	@OneToMany (mappedBy = "publicacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List <RespuestasPublicaciones> respuestas;
	
	public InicioPrincipal(NuevaPublicacion nuevaPublicacion, Usuarios usuario) {
		this.titulo = nuevaPublicacion.titulo();
		this.contenido = nuevaPublicacion.contenido();
		this.estado = EstadoPublicacion.ABIERTO;
		this.idUsuarioPublicacion = usuario;
		this.fecha = LocalDateTime.now();
	}
	
	private LocalDateTime fechaActual() {
		
		LocalDateTime fechaActual;
		DateTimeFormatter formato = new DateTimeFormatterBuilder().append(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")).toFormatter();
		return fechaActual = LocalDateTime.parse(LocalDateTime.now().toString(), formato);
		
	}
	
	public void actualizarPublicacionPorId (ActualizarPublicacion actualizarPublicacion, Usuarios usuario) {
		
		if (usuario != this.idUsuarioPublicacion || this.estado == EstadoPublicacion.OCULTO) {
			
			throw new RuntimeException("Acción no permitida");
			
			
		} else {
			
			if (actualizarPublicacion.titulo() != null) {
				this.titulo = actualizarPublicacion.titulo();
			}
			
			if (actualizarPublicacion.contenido() != null) {
				this.contenido = actualizarPublicacion.contenido();
			}
			
			if (actualizarPublicacion.estado() != null) {
				
				if (actualizarPublicacion.estado() == EstadoPublicacion.CERRADO) {
					
					this.estado = EstadoPublicacion.CERRADO;
					
				} else {
					
					this.estado = EstadoPublicacion.ABIERTO;
					
				}
					
			}
			
			this.fecha = LocalDateTime.now(); 
			
		}
		
	}
	
	public void ocultarPublicacion() {
		
		this.estado = EstadoPublicacion.OCULTO;
		
	}
	
	public void reponerPublicacion() {
		
		this.estado = EstadoPublicacion.ABIERTO;
		
	}
}
