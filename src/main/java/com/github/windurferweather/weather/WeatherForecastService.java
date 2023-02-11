package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.stream.Stream;

import static com.github.windurferweather.weather.WeatherMessageProvider.*;
import static java.util.Comparator.comparingDouble;

@Log4j2
@Service
class WeatherForecastService {

    private final WeatherForecastClient weatherForecastClient;
    private final WeatherRepository weatherRepository;

    WeatherForecastService(WeatherForecastClient weatherForecastClient, WeatherRepository weatherRepository) {
        this.weatherForecastClient = weatherForecastClient;
        this.weatherRepository = weatherRepository;
    }

    WeatherResponseDto retrieveWeatherForecast(WeatherResponse weatherResponse) {
        String city = weatherResponse.getCity();
        String country = weatherResponse.getCountry();
        double temperature = weatherResponse.getTemperature();
        double windSpeed = weatherResponse.getWindSpeed();

        WeatherResponse createWeather = new WeatherResponse(city, country, temperature, windSpeed);
        WeatherResponse weatherSaved = weatherRepository.save(createWeather);

        WeatherResponseDto weatherResponseDto = weatherForecastClient.readWeatherForLocalization(weatherSaved.getCity(), weatherSaved.getCountry());

        double wind = weatherSaved.getWindSpeed();
        double temperatureInCelcius = weatherSaved.getTemperature();
        double betterWeatherForSurfing = findBestLocalizationForSurfer(wind, temperatureInCelcius);

        return Stream.of(wind, temperatureInCelcius)
                .filter(checkBestWeather -> checkGreatWeatherForWindSurfer(wind, temperatureInCelcius))
                .map(toDto -> weatherResponseDto)
                .max(comparingDouble(weatherForSurfer -> betterWeatherForSurfing))
                .stream()
                .findAny()
                .orElseThrow(WeatherForSurferNotFoundException::new);
    }

    boolean checkGreatWeatherForWindSurfer(double wind, double temperature) {
        return wind >= MIN_WIND && wind <= MAX_WIND &&
                temperature >= MIN_TEMP && temperature <= MAX_TEMP;
    }


    void valid(String date) {
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