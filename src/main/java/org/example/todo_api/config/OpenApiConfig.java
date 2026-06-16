package org.example.todo_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI todoApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Todo API")
                        .description("REST API для управления задачами")
                        .version("1.0"));
    }
}