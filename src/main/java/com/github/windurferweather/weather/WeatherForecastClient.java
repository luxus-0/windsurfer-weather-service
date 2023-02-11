package com.github.windurferweather.weather;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.github.windurferweather.weather.WeatherMessageProvider.*;

@Service
class WeatherForecastClient {

    private final RestTemplate restTemplate = new RestTemplate();

    WeatherResponseDto readWeatherForLocalization(String city, String country) {
        return restTemplate.getForObject(WEATHER_URL + "daily?city={city}&country={country}&min_temp={minTemp}&max_temp={maxTemp}&key={api_key}",
                WeatherResponseDto.class, city, country, MIN_TEMP, MAX_TEMP, API_KEY);
    }

    WeatherResponseDto readWeatherByDate(String date) {
        return restTemplate.getForObject(WEATHER_URL + "daily?valid_date={date}&key={api_key}",
                WeatherResponseDto.class, date, API_KEY);
    }
}
