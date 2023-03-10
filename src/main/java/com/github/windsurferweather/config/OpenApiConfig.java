package com.github.windsurferweather.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Windsurfer Weather Service")
                        .description("Weather API")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Łukasz Nowogórski")
                                .email("nowogorski.lukasz0@gmail.com")));
    }

}
