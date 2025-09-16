package com.example.BasicCrud.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Basic Crud",
                version = "1.0.0",
                description = "API para gestionar usuarios con JWT y roles (Admin, User)",
                termsOfService = "http://localhost:8080/terms",
                contact = @Contact(
                        name = "Juan Sanabria",
                        email = "metagolen2003@gmail.com",
                        url = "https://github.com/JuancamiloSanabrisaVargas"
                ),
                license = @License(
                        name = "Standard Software",
                        url = "http://localhost:8080/license"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Dev Server"
                )
        },
        security = {
                @SecurityRequirement(name = "Security Token")
        }
)
@SecurityScheme(name = "Security Token" ,
 description = "Access Token For My API",
type = SecuritySchemeType.HTTP,
paramName = HttpHeaders.AUTHORIZATION,
in = SecuritySchemeIn.HEADER, scheme = "bearer",
bearerFormat = "JWT")
public class SwaggerConfig {


}
