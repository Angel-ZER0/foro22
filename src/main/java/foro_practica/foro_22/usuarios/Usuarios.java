package foro_practica.foro_22.usuarios;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import foro_practica.foro_22.modelos.InicioPrincipal;
import foro_practica.foro_22.modelos.RespuestasPublicaciones;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Usuarios implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 255, nullable = false, unique = true)
	private String nombreUsuario;
	@Column(length = 511, nullable = false, unique = true)
	private String contrasena;
	@Column(length = 255, nullable = false)
	private String correo;
	@Enumerated(EnumType.STRING)
	private Roles rol;
	@OneToMany(mappedBy = "idUsuarioPublicacion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<InicioPrincipal> publicacion;
	@OneToMany(mappedBy = "idUsuarioRespuesta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<RespuestasPublicaciones> respuestas;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(("ROLE_" + rol.name())));
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return contrasena;
	}
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return nombreUsuario;
	}
	
	private String encriptarContrasena (String dato) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(dato);
		
	}

	public Usuarios(RegistrarUsuario nuevoUsuario) {
		this.nombreUsuario = nuevoUsuario.nombreUsuario();
		this.contrasena = encriptarContrasena(nuevoUsuario.contrasena());
		this.correo = nuevoUsuario.correo();
		this.rol = Roles.USER;
	}
	
}
