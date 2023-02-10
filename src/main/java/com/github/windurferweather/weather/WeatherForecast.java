package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Log4j2
@Service
class WeatherForecast {

    private final WeatherForecastClient weatherForecastClient;

    WeatherForecast(WeatherForecastClient weatherForecastClient) {
        this.weatherForecastClient = weatherForecastClient;
    }

    public WeatherResponseDto retrieveWeatherForecast(WeatherResponse weatherResponse) {
        double temperature = weatherResponse.getTemperature();
        double windSpeed = weatherResponse.getWindSpeed();
        String city = weatherResponse.getCity();
        String country = weatherResponse.getCountry();
        String date = weatherResponse.getDate();
        if (weatherResponse.getDate() == null) {
            throw new IllegalArgumentException("Date not found");
        }
        valid(date);
        if (checkBetterWindsurfingConditions(temperature, windSpeed)) {
            return weatherForecastClient.readWeather(city, country, temperature, windSpeed);
        }
        return Optional.of(new WeatherResponseDto(null,null,0,0)).orElseThrow();
    }



    private void valid(String date) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDate parseDate = LocalDate.parse(date, formatter);
        log.info(parseDate);
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