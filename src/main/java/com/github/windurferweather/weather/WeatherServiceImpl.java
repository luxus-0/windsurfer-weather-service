package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

import static com.github.windurferweather.weather.DateValidation.valid;
import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.*;
import static java.util.Comparator.comparingDouble;

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
        LocationDto localization = findLocalization();

        WeatherResponseDto weatherResponseDto = windsurferWeatherClient.getWeatherForLocation(localization.city(), localization.country(), date);
        double windSpeed = weatherResponseDto.windSpeed();
        double temp = weatherResponseDto.temp();

        Stream.of(weatherResponseDto)
                .filter(findBestConditionByWindAndTemp -> checkBestConditionForWindSurfer(windSpeed, temp))
                .max(comparingDouble(checkBestWeather -> calculateBestWeatherForWindsurfing(windSpeed, temp)))
                .stream()
                .findAny()
                .ifPresent(showLocationWithConditionWeather -> new WeatherResponseDto(findLocalization().city(), findLocalization().country(), windSpeed, temp, date));

        return new WeatherResponseDto(findLocalization().city(), findLocalization().country(), windSpeed, temp, date);
    }

    private LocationDto findLocalization() {
        return Map.of("Jastarnia", "Polska",
                        "Bridgetown", "Barbarados",
                        "Fortaleza", "Brazylia",
                        "Pissouri", "Cypr",
                        "Le_Monre", "Mauritius")
                .entrySet()
                .stream()
                .findAny()
                .map(localized -> new LocationDto(localized.getKey(), localized.getValue()))
                .orElse(new LocationDto("", ""));
    }

    LocationDto addLocation(String city, String country) {
        WindSurferWeather localization = new WindSurferWeather();
        localization.setCity(city);
        localization.setCountry(country);

        WindSurferWeather localizationWeatherSaved = windsurferWeatherRepository.save(localization);
        return getLocationDto(city, country, localizationWeatherSaved);
    }

    private LocationDto getLocationDto(String city, String country, WindSurferWeather localizationWeatherSaved) {
        return Set.of(localizationWeatherSaved)
                .stream()
                .filter(Objects::nonNull)
                .map(toDto -> new LocationDto(city, country))
                .findAny()
                .orElse(new LocationDto("", ""));
    }

    boolean checkBestConditionForWindSurfer(double windSpeed, double temperature) {
        return windSpeed >= MIN_WIND && windSpeed <= MAX_WIND &&
                temperature >= MIN_TEMP && temperature <= MAX_TEMP;
    }

    double calculateBestWeatherForWindsurfing(double windSpeed, double temp) {
        return (windSpeed * 3) + temp;
    }

    public WindSurferWeather addWeather(WeatherResponseDto weather) {
        WindSurferWeather windSurferWeather = WindSurferWeather.builder()
                .city(weather.city())
                .country(weather.country())
                .windSpeed(weather.windSpeed())
                .temperature(weather.temp())
                .build();
        return windsurferWeatherRepository.save(windSurferWeather);
    }
}