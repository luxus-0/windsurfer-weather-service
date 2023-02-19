package com.github.windurferweather.weather;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class WindSurferWeatherApi {

    private final WeatherClient weatherClient;
    private final WeatherServiceImpl weatherService;

    WindSurferWeatherApi(WeatherClient weatherClient, WeatherServiceImpl weatherService) {
        this.weatherClient = weatherClient;
        this.weatherService = weatherService;
    }

    @GetMapping("/windsurfing_location")
    ResponseEntity<WeatherResponseDto> readWindsurfingLocation(@RequestParam String city, @RequestParam String country, @RequestParam String date) throws Exception {
        WeatherResponseDto weatherForLocation = weatherClient.getWeatherForLocation(city, country, date);
        if(weatherForLocation != null){
            return ResponseEntity.ok(weatherForLocation);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/best_windsurfing_location/{date}")
    ResponseEntity<WeatherResponseDto> readBestLocationForWindsurfing(@PathVariable String date) throws Exception {
        WeatherResponseDto bestLocationForWindsurfing = weatherService.readWindsurfingLocation(date);
        if(bestLocationForWindsurfing != null){
            return ResponseEntity.ok(bestLocationForWindsurfing);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add_weather_location/{city}/{country}")
    ResponseEntity<LocationDto> addLocation(@PathVariable String city, @PathVariable String country) {
        LocationDto location = weatherService.addLocation(city, country);
        if (location != null) {
            return ResponseEntity.ok().body(location);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add_weather")
    ResponseEntity<WindSurferWeather> addWeatherForecast(@RequestBody WeatherResponseDto weatherResponse) {
        WindSurferWeather createWeather = weatherService.addWeather(weatherResponse);
        return ResponseEntity.ok(createWeather);
    }
}
