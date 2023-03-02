package com.github.windurferweather.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.windurferweather.weather.dto.WeatherResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.stream.Stream;

import static com.github.windurferweather.weather.WeatherConstant.*;
import static java.util.Comparator.comparingDouble;

@Log4j2
@Service
class WeatherServiceImpl implements WeatherService {
    private final WeatherClient weatherClient;
    private final WeatherRepository weatherRepository;

    WeatherServiceImpl(WeatherClient weatherClient, WeatherRepository weatherRepository) {
        this.weatherClient = weatherClient;
        this.weatherRepository = weatherRepository;
    }

    @Override
    public WeatherResponseDto readTheBestLocationForWindsurfing(String date) {
        Map<String, String> locations = createLocations();

        return locations.entrySet().stream()
                .map(location -> {
                    WeatherResponseDto weatherResponse = weatherClient.getForecastWeather(location.getKey(), location.getValue(), date);

                    String city_name = weatherResponse.getCity_name();
                    String country_code = weatherResponse.getCountry_code();

                    JsonNode jsonNode = mapToJson(date, city_name, country_code);

                    double avgTemperature = getAvgTemperature(jsonNode);
                    double avgWindSpeed = getAvgWindSpeed(jsonNode);

                    return new WeatherResponseDto(city_name, country_code, avgWindSpeed, avgTemperature, date);
                })
                .filter(this::isSuitableForWindsurfingWeather)
                .max(comparingDouble(this::calculateForWindsurfingLocation))
                .map(weather -> new WeatherResponseDto(weather.getCity_name(), weather.getCountry_code(), weather.getWind_spd(), weather.getTemp(), weather.getDate()))
                .orElse(null);
    }
    Weather createWeatherForBestLocalization(String date) {
        WeatherResponseDto greatLocationForWindsurfing = readTheBestLocationForWindsurfing(date);
        Weather weather = Weather.builder()
                .cityName(greatLocationForWindsurfing.getCity_name())
                .countryCode(greatLocationForWindsurfing.getCountry_code())
                .temperature(greatLocationForWindsurfing.getTemp())
                .windSpeed(greatLocationForWindsurfing.getWind_spd())
                .build();
        return weatherRepository.save(weather);
    }

    private JsonNode mapToJson(String city_name, String country_code, String date) {
        ObjectMapper objectMapper = new ObjectMapper();
        String url_weather = getUrlWeather(city_name, country_code, date);
        String replacementUrl = url_weather.replace(" ", "%20");
        URL url = getUrl(replacementUrl);
        return getJsonNode(objectMapper, url);
    }

    private JsonNode getJsonNode(ObjectMapper objectMapper, URL url) {
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rootNode.path(DATA).get(0);
    }

    private String getUrlWeather(String city_name, String country_code, String date) {
        return ENDPOINT + "?city=" + city_name + "&country=" + country_code + "&valid_date=" + date + "&key=" + API_KEY;
    }

    private URL getUrl(String replacementUrl) {
        URL url;
        try {
            url = new URL(replacementUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return url;
    }

    private Map<String, String> createLocations() {
        return Map.of(
                "Jastarnia", "PL",
                "Bridgetown", "BB",
                "Fortaleza", "BR",
                "Pissouri", "CY",
                "Le Mont", "CH"
        );
    }

    private double calculateForWindsurfingLocation(WeatherResponseDto weather) {
        return weather.getWind_spd() * 3 + weather.getTemp();
    }

    private boolean isSuitableForWindsurfingWeather(WeatherResponseDto weatherResponse) {
        return weatherResponse.getWind_spd() >= MIN_WIND && weatherResponse.getWind_spd() <= MAX_WIND
                && weatherResponse.getTemp() >= MIN_TEMP && weatherResponse.getTemp() <= MAX_TEMP;
    }
    private double getAvgTemperature(JsonNode dataNode) {
        return Stream.of(dataNode)
                .mapToDouble(data -> data.get(TEMPERATURE).asDouble())
                .average()
                .orElse(Double.NaN);
    }

    private double getAvgWindSpeed(JsonNode dataNode) {
        return Stream.of(dataNode)
                .mapToDouble(data -> data.get(WIND_SPEED).asDouble())
                .summaryStatistics()
                .getAverage();
    }
}