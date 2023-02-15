package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.*;

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
        WeatherResponseDto weatherResponseDto =  windsurferWeatherClient.getWeatherForLocation("Jastarnia","Poland");
        log.info(weatherResponseDto);
        return weatherResponseDto;
    }

    private Set<LocationDto> addLocation() {
        LocationDto jastarnia = new LocationDto("Jastarnia", "Poland");
        LocationDto bridgetown = new LocationDto("Bridgetown", "Barbados");
        LocationDto fortaleza = new LocationDto("Fortaleza", "Brazylia");
        LocationDto pissouri = new LocationDto("Pissouri", "Cyprus");
        LocationDto le_Monre = new LocationDto("Le Monre", "Mauritius");
        return Set.of(jastarnia, bridgetown, fortaleza, pissouri, le_Monre);
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