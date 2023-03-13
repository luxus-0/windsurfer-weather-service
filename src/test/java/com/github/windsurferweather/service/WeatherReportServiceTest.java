package com.github.windsurferweather.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class WeatherReportServiceTest {

    @Autowired
    private WeatherReportService weatherReportService;

    @Test
    void shouldReturnMaxWindSpeedInDateTimePeriod(){
        String city = "Jastarnia";
        String country = "Poland";
        LocalDateTime start = LocalDateTime.of(2020, 11, 2, 2,0);
        LocalDateTime end = start.plusDays(5);

        double windSpeedForLocationInTimeFrame = weatherReportService.readMaxWindSpeedForLocationInTimeFrame(city, country, start, end);

        assertEquals("", windSpeedForLocationInTimeFrame > 0,0);
    }

}