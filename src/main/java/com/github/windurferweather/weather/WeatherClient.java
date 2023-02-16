package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.API_KEY;
import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.WEATHER_URL;

@Service
@Log4j2
class WeatherClient {

    private final RestTemplate restTemplate = new RestTemplate();
    WeatherResponseDto getWeatherForLocation(String city, String country) {
        return restTemplate.getForObject(WEATHER_URL + "daily?city={city}&country={country}&key={API_KEY}",
                WeatherResponseDto.class, city, country, API_KEY);
    }
}
