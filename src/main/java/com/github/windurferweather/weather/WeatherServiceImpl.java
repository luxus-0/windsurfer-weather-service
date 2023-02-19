package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.*;
import static java.util.Objects.requireNonNull;

@Log4j2
@Service
class WeatherServiceImpl implements WeatherService {
    private final WindsurferWeatherRepository windsurferWeatherRepository;
    private final WeatherClient windsurferWeatherClient;

    WeatherServiceImpl(WindsurferWeatherRepository windsurferWeatherRepository, WeatherClient windsurferWeatherClient) {
        this.windsurferWeatherRepository = windsurferWeatherRepository;
        this.windsurferWeatherClient = windsurferWeatherClient;
    }

    @Override
    public WeatherResponseDto readWindsurfingLocation(String date) throws Exception {
        valid(date);
        List<LocationDto> locations = getLocations();

        String city = readCity(locations);
        String country = readCountry(locations);

        WeatherResponseDto weatherResponseDto = windsurferWeatherClient.getWeatherForLocation(city, country, date);
        double windSpeed = weatherResponseDto.weatherConditionDto().getWindSpeed();
        double temp = weatherResponseDto.weatherConditionDto().getTemperature();

        LocationDto location = new LocationDto(city, country);
        WeatherConditionDto weatherCondition = new WeatherConditionDto(windSpeed, temp);

        Stream.of(weatherResponseDto)
                .filter(findBestConditionByWindAndTemp -> checkBestConditionForWindSurfer(windSpeed, temp))
                .max(Comparator.comparingDouble(checkBestWeather -> calculateBestWeatherForWindsurfing(windSpeed, temp)))
                .stream()
                .findAny()
                .ifPresent(showLocationWithConditionWeather -> new WeatherResponseDto(location, weatherCondition, date));

        return new WeatherResponseDto(location, weatherCondition, date);
    }

    private String readCountry(List<LocationDto> locations) {
        return locations
                .stream()
                .map(LocationDto::country)
                .findAny()
                .orElse("");
    }

    private String readCity(List<LocationDto> locations) {
        return locations
                .stream()
                .map(LocationDto::city)
                .findAny()
                .orElse("");
    }

    private List<LocationDto> getLocations() {
        return List.of(
                new LocationDto("Jastarnia", "Polska"),
                new LocationDto("Bridgetown", "Barbarados"),
                new LocationDto("Fortaleza", "Brazylia"),
                new LocationDto("Pissouri", "Cypr"),
                new LocationDto("Le Monre", "Mauritius"));

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

    LocationDto addLocation(String city, String country) {
        WindSurferWeather localization = new WindSurferWeather();
        localization.setCity(city);
        localization.setCountry(country);

        WindSurferWeather localizationWeatherSaved = windsurferWeatherRepository.save(localization);

        return Set.of(localizationWeatherSaved)
                .stream()
                .filter(Objects::nonNull)
                .map(toDto -> new LocationDto(city, country))
                .findAny()
                .orElse(new LocationDto("Empty city", "Empty country"));
    }

    boolean checkBestConditionForWindSurfer(double windSpeed, double temperature) {
        return windSpeed >= MIN_WIND && windSpeed <= MAX_WIND &&
                temperature >= MIN_TEMP && temperature <= MAX_TEMP;
    }

    double calculateBestWeatherForWindsurfing(double windSpeed, double temp) {
        return (windSpeed * 3) + temp;
    }
}