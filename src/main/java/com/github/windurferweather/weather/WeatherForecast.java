package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.stream.DoubleStream;

@Log4j2
@Service
class WeatherForecast implements WeatherForecastService {

    @Override
    public WeatherResponseDto retrieveWeatherForecastByDate(String date) {
        valid(date);
    }

    private void valid(String date) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDate parseDate = LocalDate.parse(date, formatter);
        log.info(parseDate);
    }

    double generateAverageTemp(double temp) {
        return DoubleStream.of(temp).average().orElseThrow();
    }

    private boolean checkBetterWindsurfingConditions(double temp, double windSpeed) {
        if (windSpeed >= 5 && windSpeed <= 18 && temp >= 5 && temp <= 35) {
            double bestLocationForSurfer = findBestLocForSurfer(windSpeed, temp);
            log.info("Best Location for surfer: " + bestLocationForSurfer);
            return true;
        }
        log.info("weather not suitable for windsurfing");
        return false;
    }

    double findBestLocForSurfer(double windSpeed, double temp) {
        return (windSpeed * 3) + temp;
    }
}