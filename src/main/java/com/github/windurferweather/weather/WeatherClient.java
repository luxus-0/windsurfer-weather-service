package com.github.windurferweather.weather;

import org.springframework.web.client.RestTemplate;

class WeatherClient {
    private final RestTemplate restTemplate;

    WeatherClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}
