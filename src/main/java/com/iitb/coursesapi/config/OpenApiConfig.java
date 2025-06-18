package com.iitb.coursesapi.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("IIT Bombay Course Management API")
                        .description("REST API for managing courses and their delivery instances")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("IIT Bombay Application Software Centre")
                                .url("https://www.iitb.ac.in"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")))
                .servers(List.of(
                        new Server().url("/").description("Default Server URL")));
    }
}
