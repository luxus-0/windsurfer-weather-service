package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static com.github.windurferweather.weather.WeatherApiKey.API_KEY;
import static com.github.windurferweather.weather.WeatherEndpoint.ENDPOINT;

@Service
@Log4j2
class WeatherClient {

    private final RestTemplate restTemplate = new RestTemplate();

    String getForecastWeather(String city) {
        String url = ENDPOINT +"?city= " + city + "&key=" + API_KEY;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        ResponseEntity<String> weatherResponse =  restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return weatherResponse.getBody();
    }
}
