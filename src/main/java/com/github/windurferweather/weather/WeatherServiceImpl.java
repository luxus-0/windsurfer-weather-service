package com.github.windurferweather.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import static com.github.windurferweather.weather.WeatherApiKey.API_KEY;
import static com.github.windurferweather.weather.WeatherConstant.*;
import static com.github.windurferweather.weather.WeatherEndpoint.ENDPOINT;

@Log4j2
@Service
class WeatherServiceImpl implements WeatherService {
    private final WindsurferWeatherRepository windsurferWeatherRepository;
    private final WeatherClient weatherClient;

    WeatherServiceImpl(WindsurferWeatherRepository windsurferWeatherRepository, WeatherClient weatherClient) {
        this.windsurferWeatherRepository = windsurferWeatherRepository;
        this.weatherClient = weatherClient;
    }

    @Override
    public WeatherConditionDto readWindsurfingLocation(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate.parse(date, dateTimeFormatter);

        List<String> locations = List.of("Jastarnia,PL", "Bridgetown,BB", "Fortaleza,BR", "Pissouri,CY", "Le Morne,MU");

        locations.stream()
                .map(location -> ENDPOINT + "?city=" + location + "&key=" + API_KEY)
                .map(weatherClient::getForecastWeather)
                .map(this::parseWeather)
                .filter(weather -> weather.date().equals(date))
                .filter(weather -> !weather.location().isEmpty())
                .filter(weather -> checkBestConditionForWindSurfer(weather.windSpeed(), weather.temperature()))
                .max(WeatherServiceImpl::calculateForBestLocation)
                .stream()
                .mapToDouble(day -> day.windSpeed() * 3 + day.temperature())
                .findAny()
                .ifPresent(location -> log.info("Best location for windsurfing: " + location));

        return new WeatherConditionDto("",0, 0, "");
    }

    private static int calculateForBestLocation(WeatherConditionDto day1, WeatherConditionDto day2) {
        double score1 = day1.windSpeed() * 3 + day1.temperature();
        double score2 = day2.windSpeed() * 3 + day2.temperature();
        return Double.compare(score1, score2);
    }

    private WeatherConditionDto parseWeather(String responseWeather) {
        JsonNode jsonNode;
        try {
            jsonNode = new ObjectMapper().readTree(responseWeather);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode dataNode = jsonNode.get(DATA);
        double averageWindSpeed = getAvgWindSpeed(dataNode);
        double averageTemperature = getAvgTemperature(dataNode);
        String date = validation_date(dataNode);
        String location = getLocation(dataNode);
        return new WeatherConditionDto(location, averageWindSpeed, averageTemperature, date);
    }

    private static String validation_date(JsonNode dataNode) {
        String validation_date = dataNode.get(0).path(VALID_DATE).asText();
        return LocalDate.parse(validation_date, DateTimeFormatter.ISO_LOCAL_DATE).toString();
    }

    private static double getAvgTemperature(JsonNode dataNode) {
        return Stream.of(dataNode)
                .mapToDouble(data -> data.get(TEMPERATURE).asDouble())
                .average()
                .orElse(Double.NaN);
    }

    private static double getAvgWindSpeed(JsonNode dataNode) {
        return Stream.of(dataNode)
                .mapToDouble(data -> data.get(WIND_SPEED).asDouble())
                .summaryStatistics()
                .getAverage();
    }

    private static String getLocation(JsonNode dataNode){
        return Stream.of(dataNode)
                .map(data -> data.get(CITY).asText())
                .findFirst()
                .orElse(null);
    }

    boolean checkBestConditionForWindSurfer(double windSpeed, double temperature) {
        return windSpeed >= MIN_WIND && windSpeed <= MAX_WIND &&
                temperature >= MIN_TEMP && temperature <= MAX_TEMP;
    }

    public ForecastWeather addWeather(WeatherResponseDto weather) {
        ForecastWeather forecastWeather = ForecastWeather.builder()
                .city_name(weather.city())
                .country_name(weather.country())
                .windSpeed(weather.windSpeed())
                .temperature(weather.temp())
                .build();
        return windsurferWeatherRepository.save(forecastWeather);
    }
}