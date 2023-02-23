package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
class WeatherForecastController {
    private final WeatherServiceImpl weatherService;


    WeatherForecastController(WeatherServiceImpl weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("location_for_windsurfing/{date}")
    ResponseEntity<WeatherResponseDto> readBestLocationForWindsurfing(@PathVariable String date) throws Exception {
        WeatherResponseDto weatherResponseDto = weatherService.readWindsurfingLocation(date);
        if(weatherResponseDto != null){
            return ResponseEntity.ok(weatherResponseDto);
        }
        return ResponseEntity.notFound().build();
    }
}
