package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Set;

import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.*;
import static java.util.Objects.requireNonNull;

@Log4j2
@Service
class WeatherServiceImpl implements WeatherService {
    private final WindsurferWeatherRepository windsurferWeatherRepository;
    private final WeatherClient windsurferWeatherClient;

    @Value("${weather.url}")
    private String url;

    @Value("${weather.apiKey}")
    private String apiKey;

    WeatherServiceImpl(WindsurferWeatherRepository windsurferWeatherRepository, WeatherClient windsurferWeatherClient) {
        this.windsurferWeatherRepository = windsurferWeatherRepository;
        this.windsurferWeatherClient = windsurferWeatherClient;
    }

    @Override
    public WeatherResponseDto readWindsurfingLocation(String date) {
        valid(date);
        Set<LocationDto> locations = addLocation();
        String city = locations.stream().map(LocationDto::city).findAny().orElse("");
        String country = locations.stream().map(LocationDto::country).findAny().orElseThrow();

        WeatherResponseDto weatherResponseDto =  windsurferWeatherClient.getWeatherForLocation(city,country);
        log.info(weatherResponseDto);
        return weatherResponseDto;
    }

    ResponseEntity<?> valid(String date) {
        requireNonNull(date);
        LocalDate dateWeather;
        try {
           dateWeather = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
           return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(dateWeather);
    }

    private Set<LocationDto> addLocation() {
        LocationDto jastarnia = new LocationDto("Jastarnia", "Poland");
        LocationDto bridgetown = new LocationDto("Bridgetown", "Barbados");
        LocationDto fortaleza = new LocationDto("Fortaleza", "Brazylia");
        LocationDto pissouri = new LocationDto("Pissouri", "Cyprus");
        LocationDto le_monre = new LocationDto("Le Monre", "Mauritius");
        return Set.of(jastarnia, bridgetown, fortaleza, pissouri, le_monre);
    }

    LocationDto addLocation(String city, String country) {
        WindSurferWeather localization = new WindSurferWeather();
        localization.setCity(city);
        localization.setCountry(country);

        WindSurferWeather localizationWeatherSaved = windsurferWeatherRepository.save(localization);

        return getLocation(city, country, localizationWeatherSaved);
    }

    private static LocationDto getLocation(String city, String country, WindSurferWeather localizationWeatherSaved) {
        return Set.of(localizationWeatherSaved).stream()
                .filter(Objects::nonNull)
                .map(toDto -> new LocationDto(city, country))
                .findAny()
                .orElse(new LocationDto("", ""));
    }

    boolean checkBestPlaceForWindSurfer(double windSpeed, double temperature) {
        return windSpeed >= MIN_WIND && windSpeed <= MAX_WIND &&
                temperature >= MIN_TEMP && temperature <= MAX_TEMP;
    }

    double findBestLocalizationForSurfer(double windSpeed, double temp) {
        return (windSpeed * 3) + temp;
    }
}