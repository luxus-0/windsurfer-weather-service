package com.github.windurferweather.weather;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.*;

@Service
class WindSurferWeatherClient {

    private final RestTemplate restTemplate = new RestTemplate();

    WindSurferWeatherDto readWindSurfingLocation(String city, String country) {
        String weatherUrl = getWeatherUrl(city, country);
        restTemplate.getForObject(weatherUrl, WindSurferWeatherDto.class, city, country, API_KEY);

        return WindSurferWeatherDto.builder()
                .city(city)
                .country(country)
                .build();
    }
}
