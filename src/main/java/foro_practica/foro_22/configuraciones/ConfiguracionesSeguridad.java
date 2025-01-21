package foro_practica.foro_22.configuraciones;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.util.UriComponentsBuilder;

import foro_practica.foro_22.usuarios.Roles;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfiguracionesSeguridad {

	private final FiltroDeSeguridad filtro;
	
	private static final String[] URL_LISTA_BLAMCA = { "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs",
			"/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html/**", "/api/auth/**",
			"/api/test/**", "/authenticate" };
	private static final String [] INICIO_SESION_REGISTRO = {"/usuario/registrar", "/usuario/accesar"};
	private static final String [] ACCESO_PUBLICACIONES_RESPUESTAS = {"/**", "/inicio/**"};
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(URL_LISTA_BLAMCA).permitAll()
						.requestMatchers(HttpMethod.POST, INICIO_SESION_REGISTRO).permitAll()
						.requestMatchers(HttpMethod.GET, ACCESO_PUBLICACIONES_RESPUESTAS).permitAll()
						.requestMatchers(HttpMethod.POST, "/inicio/**").authenticated()
						.requestMatchers(HttpMethod.POST, "/usuario/registrar-administrador").hasRole(Roles.ADMIN.toString())
						.requestMatchers(HttpMethod.DELETE, "/ocultar-publicacion/**").hasRole(Roles.ADMIN.toString())
						.requestMatchers(HttpMethod.PATCH, "/reponer-publicacion/**").hasRole(Roles.ADMIN.toString())
						.anyRequest().authenticated())
				.addFilterBefore(filtro, UsernamePasswordAuthenticationFilter.class).build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {

		return authenticationConfiguration.getAuthenticationManager();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}
	
	@Bean
    public UriComponentsBuilder uriComponentsBuilder() {
        return UriComponentsBuilder.newInstance();
    }

}
