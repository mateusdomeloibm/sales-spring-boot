package com.ibm.mateusmelo.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .components(
                        new Components().addSecuritySchemes(
                                "Bearer Authorization", securityScheme()
                        )
                ).addSecurityItem(
                        new SecurityRequirement()
                                .addList("Bearer Authorization")
                );
    }

    private Info apiInfo() {
        return new Info()
                .title("Sales API")
                .description("Api built for Sales application")
                .contact(contactInfo());
    }

    private Contact contactInfo() {
        return new Contact()
                .name("Mateus Melo")
                .email("mateusmelo@gmail.com")
                .url("https://github.com/mateusdomelo");
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}
