package com.github.windurferweather.weather;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WindSurferWeatherApi {

    private final WeatherServiceImpl windSurferServiceImpl;

    WindSurferWeatherApi(WeatherServiceImpl windSurferServiceImpl) {
        this.windSurferServiceImpl = windSurferServiceImpl;
    }

    @GetMapping("/windsurfing_Location/{date}")
    ResponseEntity<WeatherResponseDto> readWindsurfingLocation(@PathVariable String date) {
        WeatherResponseDto bestLocationForWindsurfing = windSurferServiceImpl.readWindsurfingLocation(date);
        if(bestLocationForWindsurfing != null){
            return ResponseEntity.ok(bestLocationForWindsurfing);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create_location_weather/{city}/{country}")
    ResponseEntity<LocationDto> addLocation(@PathVariable String city, @PathVariable String country) {
        LocationDto location = windSurferServiceImpl.addLocation(city, country);
        if (location != null) {
            return ResponseEntity.ok().body(location);
        }
        return ResponseEntity.notFound().build();
    }
}
