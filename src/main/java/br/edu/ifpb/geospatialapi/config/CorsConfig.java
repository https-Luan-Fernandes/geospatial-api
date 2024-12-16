package br.edu.ifpb.geospatialapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Adiciona CORS para todas as rotas
                .allowedOrigins("http://localhost:63342") // Permite o frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Permite os métodos HTTP que você usa
                .allowCredentials(true); // Permite o envio de credenciais se necessário
    }
}
