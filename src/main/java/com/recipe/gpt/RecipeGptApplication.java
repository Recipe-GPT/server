package com.recipe.gpt;

import com.recipe.gpt.common.config.properties.GoogleProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@EnableConfigurationProperties(GoogleProperties.class)
@SpringBootApplication
public class RecipeGptApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeGptApplication.class, args);
    }

}
