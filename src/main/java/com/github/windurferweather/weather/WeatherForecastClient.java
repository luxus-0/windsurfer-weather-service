package com.github.windurferweather.weather;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.github.windurferweather.weather.WeatherMessageProvider.API_KEY;
import static com.github.windurferweather.weather.WeatherMessageProvider.WEATHER_URL;

@Component
class WeatherForecastClient {

    private final RestTemplate restTemplate;

    WeatherForecastClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    WeatherResponseDto readWeather(String city, String country, double temperature, double windSpeed) {
        return restTemplate.getForObject(WEATHER_URL + "daily?city={city}&country={country}&temp={temperature}&windSpeed={wind_spd}&key={api_key}",
                WeatherResponseDto.class, city, country, temperature, windSpeed, API_KEY);
    }

    WeatherResponseDto readWeatherByDate(String date) {
        return restTemplate.getForObject(WEATHER_URL + "daily?valid_date={date}&key={api_key}",
                WeatherResponseDto.class, date, API_KEY);
    }
}
