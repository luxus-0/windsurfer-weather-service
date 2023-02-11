package com.github.windurferweather.weather;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WeatherApi {

    private final WeatherForecastService weatherForecastService;

    WeatherApi(WeatherForecastService weatherForecastService) {
        this.weatherForecastService = weatherForecastService;
    }

    @GetMapping("/weather")
    WeatherResponseDto getWeather(@RequestBody WeatherResponse weatherResponse){
        return weatherForecastService.retrieveWeatherForecast(weatherResponse);
    }
}
