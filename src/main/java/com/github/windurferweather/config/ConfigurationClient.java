package com.github.windurferweather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigurationClient {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
