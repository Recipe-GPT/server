package com.recipe.gpt;

import com.recipe.gpt.common.config.properties.GoogleProperties;
import com.recipe.gpt.common.config.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableConfigurationProperties({GoogleProperties.class, JwtProperties.class})
@SpringBootApplication
public class RecipeGptApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeGptApplication.class, args);
    }

}
