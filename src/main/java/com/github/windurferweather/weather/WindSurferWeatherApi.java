package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.github.windurferweather.weather.BestLocationWeatherForWindsurferMessage.showBestLocationForWindsurfer;

@RestController
@Log4j2
class WindSurferWeatherApi {
    private final WeatherServiceImpl weatherService;

    WindSurferWeatherApi(WeatherServiceImpl weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/best_windsurfing_location/{date}")
    ResponseEntity<WeatherConditionDto> readBestLocationForWindsurfing(@PathVariable String date) {
        WeatherConditionDto bestLocationForWindsurfing = weatherService.readWindsurfingLocation(date);
        if (bestLocationForWindsurfing != null) {
            showBestLocationForWindsurfer(bestLocationForWindsurfing);
            return ResponseEntity.ok(bestLocationForWindsurfing);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add_weather")
    ResponseEntity<ForecastWeather> addWeatherForecast(@RequestBody WeatherResponseDto weatherResponse) {
        ForecastWeather createWeather = weatherService.addWeather(weatherResponse);
        return ResponseEntity.ok(createWeather);
    }
}
