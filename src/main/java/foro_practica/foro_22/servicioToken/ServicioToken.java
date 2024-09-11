package foro_practica.foro_22.servicioToken;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import foro_practica.foro_22.usuarios.Usuarios;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioToken {

	@Value("${api.secreto.encriptar}")
	private String fragEncriptador;
	@Value("${api.issuer}")
	private String firma;
	private LocalDateTime duracionToken = LocalDateTime.now().plusDays(1);
	Date expiracion = Date.from(duracionToken.atZone(ZoneId.systemDefault()).toInstant());
	
	private final HttpServletRequest request;
	
	public String creadorToken(Usuarios usuario) {
	
		try {
			Algorithm algorithm = Algorithm.HMAC256(fragEncriptador);
			return JWT.create().withIssuer(firma).withSubject(usuario.getUsername()).withClaim("id", usuario.getId()).withExpiresAt(expiracion).sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException();
		}
		
	}
	
	public String getNombreUsuario (String token) {
		
		if (token == null) {
			throw new RuntimeException();
		}
		
		DecodedJWT verificador = null;
		
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(fragEncriptador);
			verificador = JWT.require(algorithm).withIssuer(firma).build().verify(token);
			verificador.getSubject();
			
		} catch (JWTVerificationException exception) {
			
			System.out.println(exception.toString());
			
		}
		
		if (verificador.getSubject() == null) {
			
			throw new RuntimeException();
			
		}
		
		return verificador.getSubject();
		
	}
	
	public Long getIdUsuario (String token) {
		
		if (token == null) {
			throw new RuntimeException();
		}
		
		DecodedJWT verificador = null;
		
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(fragEncriptador);
			verificador = JWT.require(algorithm).withIssuer(firma).build().verify(token);
			verificador.getClaim("id");
			
		} catch (JWTVerificationException exception) {
			
			System.out.println(exception.toString());
			
		}
		
		if (verificador.getClaim("id") == null) {
			
			throw new RuntimeException();
			
		}
		
		return verificador.getClaim("id").asLong();
		
	}
	
	public String conseguirToken() {
		
		if (request.getHeader(HttpHeaders.AUTHORIZATION) == null) {
		
			throw new RuntimeException();
			
		} else {
			
			return request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
			
		}
		
	}
	
}
