package com.github.windsurferweather.controller;

import com.github.windsurferweather.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.windsurferweather.utils.WeatherConstant.DATE_REGEX;

@RestController
@RequestMapping("/api/v1/surfing-places")
public class WeatherController {
    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping("/{date}")
    public ResponseEntity<?> getWeather(@PathVariable String date) {
        if (!date.matches(DATE_REGEX))
            return new ResponseEntity<>("Niepoprawna data, proszę o datę w formacie yyyy-MM-dd", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(service.collectBestPlaceToSurfForEachDay(date), HttpStatus.OK);
    }
}

