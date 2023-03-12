package com.github.windsurferweather.controller;

import com.github.windsurferweather.model.WeatherReport;
import com.github.windsurferweather.model.dto.WeatherReportDto;
import com.github.windsurferweather.service.WeatherReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/weather-report")
public class WeatherReportController {

    private final WeatherReportService weatherReportService;

    public WeatherReportController(WeatherReportService weatherReportService) {
        this.weatherReportService = weatherReportService;
    }

    @GetMapping("")
    List<WeatherReport> findWeatherReport() {
        return weatherReportService.readWeatherReport();
    }

    @PostMapping
    WeatherReportDto makeWeatherReport(@RequestBody WeatherReport weatherReport) {
        return weatherReportService.formWeatherReport(weatherReport);
    }

    @GetMapping("/nearest_location")
    ResponseEntity<?> findWeatherReportForLocationsNearby(@RequestParam String city, @RequestParam String country, @RequestParam LocalDateTime start, @RequestParam LocalDateTime end){
        Double avgTempByTimeFrame =  weatherReportService.readAverageTemperatureForLocationInTimeFrame(city, country, start, end);
        if(avgTempByTimeFrame != null) {
            return new ResponseEntity<>(avgTempByTimeFrame, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
