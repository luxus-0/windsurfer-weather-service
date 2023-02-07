package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Log4j2
@Service
class WeatherForecastImpl implements WeatherForecastService {

    private static final String WEATHER_URL = "";
    private final RestTemplate restTemplate;
    private final DateValidation dateValidation;
    private List<CityResponse> cities;

    WeatherForecastImpl(RestTemplate restTemplate, DateValidation dateValidation) {
        this.restTemplate = restTemplate;
        this.dateValidation = dateValidation;
    }

    void addLocation(String city, String country) {
        cities.add(CityResponse.builder()
                        .city(city)
                        .country(country)
                .build());
    }

    @Override
    public WeatherResponseDto retrieveWeatherForecastByDate(String date) {
            String city = cities.stream()
                    .map(CityResponse::getCity)
                    .findAny()
                    .orElseThrow();

            String weather_api = String.format(WEATHER_URL);
            WeatherResponse weatherResponse = restTemplate.getForObject(weather_api, WeatherResponse.class);
            List<WeatherResponse> weatherResponses = new ArrayList<>();
            weatherResponses.add(weatherResponse);

            double windSpeed = readWindSpeed(weatherResponses);
            double avgTemp = readAvgTemp(weatherResponses);

            dateValidation.isValid(date);


            Stream.of(windSpeed, avgTemp, city)
                    .filter(checkWeather -> windSpeed > 5 && windSpeed < 18
                            && avgTemp > 5 && avgTemp < 35)
                    .findAny()
                    .ifPresentOrElse(showTemp -> generateWeather(city, windSpeed, avgTemp),
                            () -> log.info("Weather argument not found"));
        return new WeatherResponseDto(windSpeed, avgTemp, city);
    }

    private WeatherResponse.WeatherResponseBuilder generateWeather(String city, double windSpeed, double avgTemp) {
        return WeatherResponse.builder()
                .windSpeed(windSpeed)
                .avgTemp(avgTemp)
                .city(CityResponse.builder()
                        .city(city)
                        .build());
    }

    private static Double readAvgTemp(List<WeatherResponse> weatherResponses) {
        return weatherResponses.stream()
                .map(WeatherResponse::getAvgTemp)
                .findAny().orElseThrow();
    }

    private double readWindSpeed(List<WeatherResponse> weatherResponses) {
        return weatherResponses.stream()
                .map(WeatherResponse::getWindSpeed)
                .findAny()
                .orElseThrow();
    }

    private float findBestLocForSurfer(double wind_speed, double temperature) {
        return (float) ((wind_speed * 3) + temperature);
    }
}