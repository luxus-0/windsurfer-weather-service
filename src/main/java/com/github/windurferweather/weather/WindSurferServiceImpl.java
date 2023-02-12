package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.github.windurferweather.weather.WeatherMessageProvider.*;
import static java.util.Comparator.comparingDouble;

@Log4j2
@Service
class WindSurferServiceImpl implements WindSurferLocationService {

    private final WeatherForecastClient weatherForecastClient;
    private final ValidationDate validationDate;


    WindSurferServiceImpl(WeatherForecastClient weatherForecastClient, ValidationDate validationDate) {
        this.weatherForecastClient = weatherForecastClient;
        this.validationDate = validationDate;
    }

    @Override
    public WindSurferWeatherDto readWindSurfingLocationByDate(String date) {
        Stream.of(date)
                .filter(checkDate -> date.isEmpty() || !validationDate.isValid(date))
                .findAny()
                .ifPresent(p -> log.info("Date is null or incorrect format"));

        Map<String, String> localization = getLocalization();

        String city = localization.keySet().stream().findAny().orElse("City not found");
        String country = localization.values().stream().findAny().orElse("Country not found");

        WindSurferWeatherDto windSurferWeatherDto = weatherForecastClient.readWindSurfingLocation(city, country);

        double windSpeed = windSurferWeatherDto.windSpeed();
        double temperature = windSurferWeatherDto.temperature();

        return Stream.of(windSurferWeatherDto)
                .filter(checkBestWeather -> checkGreatWeatherForWindSurfer(windSpeed, temperature))
                .max(comparingDouble(weatherForSurfer -> findBestLocalizationForSurfer(windSpeed, temperature)))
                .stream()
                .map(dto -> windSurferWeatherDto)
                .findAny()
                .orElseThrow(WeatherForSurferNotFoundException::new);
    }

    private static Map<String, String> getLocalization() {
        Map<String, String> localization = new HashMap<>();
        localization.put("Jastarnia", "Poland");
        localization.put("Bridgetown","Barbarados");
        localization.put("Fortaleza","Brasil");
        localization.put("Pissouri","Cyprus");
        localization.put("Le Monre","Mauritius");
        return localization;
    }

    boolean checkGreatWeatherForWindSurfer(double wind, double temperature) {
        return wind >= MIN_WIND && wind <= MAX_WIND &&
                temperature >= MIN_TEMP && temperature <= MAX_TEMP;
    }

    double findBestLocalizationForSurfer(double windSpeed, double temp) {
        return (windSpeed * 3) + temp;
    }
}