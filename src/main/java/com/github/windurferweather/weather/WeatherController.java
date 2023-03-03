package com.github.windurferweather.weather;

import com.github.windurferweather.weather.dto.WeatherResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
class WeatherController {
    private final WeatherServiceImpl weatherService;
    WeatherController(WeatherServiceImpl weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("location_for_windsurfing/{date}")
    ResponseEntity<WeatherResponseDto> readBestLocationForWindsurfing(@PathVariable String date) {
        WeatherResponseDto weatherResponseDto = weatherService.readTheBestLocationForWindsurfing(date);
        if(weatherResponseDto != null){
            return ResponseEntity.ok(weatherResponseDto);
        }
        return ResponseEntity.notFound().build();
    }
}

