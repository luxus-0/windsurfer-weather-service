package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Comparator.comparingDouble;
import static java.util.Objects.requireNonNull;

@Log4j2
@Service
class WeatherForecast {

    private final WeatherForecastClient weatherForecastClient;

    WeatherForecast(WeatherForecastClient weatherForecastClient) {
        this.weatherForecastClient = weatherForecastClient;
    }

    public WeatherResponseDto retrieveWeatherForecast(WeatherResponse weatherResponse) {
        String date = weatherResponse.getDate();
        if (date == null) {
            throw new IllegalArgumentException("Date not found");
        }
        valid(date);
        String cityName = weatherResponse.getCity();
        String countryName = weatherResponse.getCountry();
        double wind = weatherResponse.getWindSpeed();
        double temperatureInCelcius = weatherResponse.getTemperature();
        double betterWeatherForSurfing = findBestLocalizationForSurfer(wind, temperatureInCelcius);

       WeatherResponseDto weatherDto = new WeatherResponseDto(cityName, countryName, temperatureInCelcius, wind);

        Stream.of(wind, temperatureInCelcius)
                .filter(checkBestWeather -> checkConditionWeather(weatherResponse))
                .findAny()
                .map(toDto -> weatherDto)
                .stream()
                .max(comparingDouble(weatherForSurfer -> betterWeatherForSurfing))
                .ifPresentOrElse(message -> log.info("weather suitable for windsurfing"),
                        () -> log.info("weather not suitable for windsurfing"));
        return Optional.of(new WeatherResponseDto(cityName, countryName, temperatureInCelcius, wind))
                .orElseThrow();
    }

    private boolean checkConditionWeather(WeatherResponse weatherResponse) {
        return weatherResponse.getWindSpeed() >= 5 && weatherResponse.getWindSpeed() <= 18 &&
                weatherResponse.getTemperature() >= 5 && weatherResponse.getTemperature() <= 35;
    }


    private void valid(String date) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDate parseDate = LocalDate.parse(date, formatter);
        log.info(parseDate);
    }

    double findBestLocalizationForSurfer(double windSpeed, double temp) {
        return (windSpeed * 3) + temp;
    }
}