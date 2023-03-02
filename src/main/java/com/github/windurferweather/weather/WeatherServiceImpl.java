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

    WeatherServiceImpl(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    @Override
    public WeatherResponseDto readTheBestLocationForWindsurfing(String date) {
        Map<String, String> locations = createLocations();

        return locations.entrySet().stream()
                .map(location -> {
                    WeatherResponseDto weatherResponse = weatherClient.getForecastWeather(location.getKey(), location.getValue(), date);

                    String city_name = weatherResponse.getCity_name();
                    String country_code = weatherResponse.getCountry_code();

                    ObjectMapper objectMapper = new ObjectMapper();
                    String url_weather = getUrlWeather(date, city_name, country_code);
                    String replacementUrl = url_weather.replace(" ", "%20");
                    URL url = getUrl(replacementUrl);
                    JsonNode data = getJsonNode(objectMapper, url);

                    double avgTemperature = getAvgTemperature(data);
                    double avgWindSpeed = getAvgWindSpeed(data);

                    return new WeatherResponseDto(city_name, country_code, avgWindSpeed, avgTemperature, date);
                })
                .filter(this::isSuitableForWindsurfingWeather)
                .max(comparingDouble(this::calculateForWindsurfingLocation))
                .map(location -> new WeatherResponseDto(location.getCity_name(), location.getCountry_code(), location.getWind_spd(), location.getTemp(), location.getDate()))
                .orElse(null);
    }

    private String getUrlWeather(String date, String city_name, String country_code) {
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

    private JsonNode getJsonNode(ObjectMapper objectMapper, URL url) {
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rootNode.path(DATA).get(0);
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

    private boolean isSuitableForWindsurfingWeather(WeatherResponseDto locationData) {
        return locationData.getWind_spd() >= MIN_WIND && locationData.getWind_spd() <= MAX_WIND
                && locationData.getTemp() >= MIN_TEMP && locationData.getTemp() <= MAX_TEMP;
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