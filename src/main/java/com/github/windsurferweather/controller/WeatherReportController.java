package com.github.windsurferweather.controller;

import com.github.windsurferweather.model.WeatherReport;
import com.github.windsurferweather.model.dto.WeatherReportDto;
import com.github.windsurferweather.service.WeatherReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/weather-report")
public class WeatherReportController {

    private final WeatherReportService weatherReportService;

    public WeatherReportController(WeatherReportService weatherReportService) {
        this.weatherReportService = weatherReportService;
    }

    @GetMapping
    List<WeatherReport> findWeatherReport() {
        return weatherReportService.readWeatherReport();
    }

    @PostMapping
    WeatherReportDto makeWeatherReport(@RequestBody WeatherReport weatherReport) {
        return weatherReportService.formWeatherReport(weatherReport);
    }

    @GetMapping
    ResponseEntity<Double> findMaxWindSpeedInPeriodTime(@RequestParam String city, @RequestParam String country, @RequestParam LocalTime start, @RequestParam LocalTime end){
        Double maxWindSeedInTimePeriod = weatherReportService.readMaxWindSpeedForLocationInTimeFrame(city, country, start, end);
        if(maxWindSeedInTimePeriod != null) {
            return new ResponseEntity<>(maxWindSeedInTimePeriod, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    ResponseEntity<?> findWeatherReportForLocationsNearby(@RequestParam double lat, @RequestParam double lon, @RequestParam double distance){
        List<WeatherReportDto> weatherReportDtos =  weatherReportService.readWeatherReportForLocationsNearby(lat, lon, distance);
        if(weatherReportDtos != null) {
            return new ResponseEntity<>(weatherReportDtos, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    ResponseEntity<?> findWeatherReportForLocationsNearby(@RequestParam String city, @RequestParam String country, @RequestParam LocalTime start, @RequestParam LocalTime end){
        Double avgTempByTimeFrame =  weatherReportService.readAverageTemperatureForLocationInTimeFrame(city, country, start, end);
        if(avgTempByTimeFrame != null) {
            return new ResponseEntity<>(avgTempByTimeFrame, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
