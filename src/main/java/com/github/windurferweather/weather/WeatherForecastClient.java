package com.github.windurferweather.weather;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.github.windurferweather.weather.WeatherMessageProvider.API_KEY;
import static com.github.windurferweather.weather.WeatherMessageProvider.WEATHER_URL;

@Service
class WeatherForecastClient {

    private final RestTemplate restTemplate = new RestTemplate();

    WeatherResponseDto readWeatherForLocalization(String city, String country) {
       return restTemplate.getForObject(WEATHER_URL + "daily?city={city}&country={country}&key={api_key}",
                WeatherResponseDto.class, city, country, API_KEY);
    }

    WeatherResponseDto readWeatherForCondition(double temperature, double windSpeed) {
        return restTemplate.getForObject(WEATHER_URL + "daily?temp={temperature}&windSpeed={windSpeed}&key={api_key}",
                WeatherResponseDto.class, temperature, windSpeed, API_KEY);
    }

    WeatherResponseDto readWeatherByDate(String date) {
        return restTemplate.getForObject(WEATHER_URL + "daily?valid_date={date}&key={api_key}",
                WeatherResponseDto.class, date, API_KEY);
    }
}
