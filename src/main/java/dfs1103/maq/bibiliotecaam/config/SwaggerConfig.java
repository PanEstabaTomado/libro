package dfs1103.maq.bibiliotecaam.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info().title("API 2026 Libros de la Biblioteca AM")
                        .version("1.0")
                        .description("Dcoumentacion de la API para el sistema de libros de la Biblioteca AM"));
    }
}
