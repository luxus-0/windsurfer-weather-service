package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.*;
import static java.util.Comparator.comparingDouble;

@Log4j2
@Service
class WindSurferServiceImpl implements WindSurferLocationService {

    private final WindSurferWeatherClient windSurferWeatherClient;
    private final DateValidation dateValidation;


    WindSurferServiceImpl(WindSurferWeatherClient windSurferWeatherClient, DateValidation dateValidation) {
        this.windSurferWeatherClient = windSurferWeatherClient;
        this.dateValidation = dateValidation;
    }

    @Override
    public WindSurferWeatherDto readWindSurfingLocationByDate(String date) {
        Stream.of(date)
                .filter(checkDate -> isValid(date))
                .findAny()
                .ifPresent(p -> log.info("Date is null or incorrect format"));

        Map<String, String> localization = readPlaceForWindSurfer();

        String city = localization.keySet().stream().findAny().orElse("City not found");
        String country = localization.values().stream().findAny().orElse("Country not found");

        WindSurferWeatherDto windSurferWeatherDto = windSurferWeatherClient.readWindSurfingLocation(city, country);
        double windSpeed = windSurferWeatherDto.windSpeed();
        double temperature = windSurferWeatherDto.temperature();

        return Stream.of(windSpeed, temperature)
                .filter(checkBestWeather -> checkBestPlaceForWindSurfer(windSpeed, temperature))
                .max(comparingDouble(weatherForSurfer -> findBestLocalizationForSurfer(windSpeed, temperature)))
                .map(dto -> new WindSurferWeatherDto(city, country, windSpeed, temperature))
                .stream()
                .findAny()
                .orElse(new WindSurferWeatherDto(city, country, windSpeed, temperature));
    }

    private boolean isValid(String date) {
        return date.isEmpty() || !dateValidation.isValid(date) || dateValidation.isInRange(date);
    }

    private Map<String, String> readPlaceForWindSurfer() {
        Map<String, String> localization = new HashMap<>();
        localization.put("Jastarnia", "Poland");
        localization.put("Bridgetown","Barbarados");
        localization.put("Fortaleza","Brasil");
        localization.put("Pissouri","Cyprus");
        localization.put("Le Monre","Mauritius");
        return localization;
    }

    boolean checkBestPlaceForWindSurfer(double wind, double temperature) {
        return wind >= MIN_WIND && wind <= MAX_WIND &&
                temperature >= MIN_TEMP && temperature <= MAX_TEMP;
    }

    double findBestLocalizationForSurfer(double windSpeed, double temp) {
        return (windSpeed * 3) + temp;
    }
}