package com.FirstEmployeesProject.Config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
@Configuration
public class SwaggerConfig {
	
	private static final String BEARER_FORMAT = "JWT";
	private static final String SCHEME = "Bearer";
	private static final String SECURITY_SCHEME_NAME = "Security Scheme";
	@Bean
	public OpenAPI api() {
		return new OpenAPI().schemaRequirement(SECURITY_SCHEME_NAME, getSecurityScheme())
				.security(getSecurityRequirement()).info(info());
		// .servers(List.of(new Server().url("https://api1.pcventure.in"))); // Ensure
		// HTTPS

	}

	private Info info() {
		return new Info().title("Employee Management API").description("This is a sample API for managing employees.")
				.version("1.0.0").contact(new Contact().name("Vaishnavi Bakal").email("vaishu@gmail.com").url(""));
	}

	private List<SecurityRequirement> getSecurityRequirement() {
		SecurityRequirement securityRequirement = new SecurityRequirement();
		securityRequirement.addList(SECURITY_SCHEME_NAME);
		return List.of(securityRequirement);
	}

	private SecurityScheme getSecurityScheme() {
		SecurityScheme securityScheme = new SecurityScheme();
		securityScheme.bearerFormat(BEARER_FORMAT);
		securityScheme.type(SecurityScheme.Type.HTTP);
		securityScheme.in(SecurityScheme.In.HEADER);
		securityScheme.scheme(SCHEME);
		return securityScheme;
	}


}
