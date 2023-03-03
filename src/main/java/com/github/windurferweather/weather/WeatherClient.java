package com.github.windurferweather.weather;

import com.github.windurferweather.weather.dto.LocationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.github.windurferweather.weather.WeatherConstant.*;

@Service
class WeatherClient {

 private final RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> getForecastWeather(LocationDto locationDto) {

        return restTemplate.getForEntity(ENDPOINT +
                "?city=" + locationDto.city() +
                "&country=" + locationDto.country() +
                "&lat=" + locationDto.lat() +
                "&lon=" + locationDto.lon() +
                "&key=" + API_KEY, String.class);
    }
}
