package foro_practica.foro_22.swaggerDocumentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerDocumentation {

	@Bean
	public OpenAPI customOpenAPI() {
		
		return new OpenAPI()
				.info(new Info().title("Foro 22"))				
				.addSecurityItem(new SecurityRequirement().addList("Foro 22 autenticacióna"))
				.components(new Components().addSecuritySchemes("Foro 22 autenticación", new SecurityScheme()
						.name("Foro 22 autenticación").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
		
	}
	
}
