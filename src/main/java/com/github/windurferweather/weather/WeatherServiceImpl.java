package com.github.windurferweather.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Stream;

import static com.github.windurferweather.weather.LocationCreator.createLocations;
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
    public WeatherResponseDto readWindsurfingLocation(String date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate.parse(date, dateFormat);

        Map<String, String> locations = createLocations();

        return locations.entrySet().stream()
                .map(location -> {
                    WeatherResponseDto weatherResponse = weatherClient.getForecastWeather(location.getKey(), location.getValue());

                    String city_name = weatherResponse.getCity_name();
                    String country_code = weatherResponse.getCountry_code();

                    JsonNode jsonNode = getJsonNode(city_name, country_code);

                    double avgTemperature = getAvgTemperature(jsonNode);
                    double avgWindSpeed = getAvgWindSpeed(jsonNode);

                    return new WeatherResponseDto(city_name, country_code, avgWindSpeed, avgTemperature, date);
                })
                .filter(this::isSuitableForWindsurfingWeather)
                .max(comparingDouble(this::calculateForWindsurfingLocation))
                .map(location -> new WeatherResponseDto(location.getCity_name(), location.getCountry_code(), location.getWind_spd(), location.getTemp(), location.getDate()))
                .orElse(null);
    }

    private JsonNode getJsonNode(String city_name, String country_code) {
        JsonNode jsonNode;
        try {
            jsonNode = getNode(city_name, country_code);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonNode;
    }

    private JsonNode getNode(String city_name, String country_code) throws Exception {
            ObjectMapper objectMapper = new ObjectMapper();
            String url_weather = getUrl(city_name, country_code);
            String replacementUrl = url_weather.replace(" ", "%20");
            URL url = new URL(replacementUrl);
            JsonNode rootNode = objectMapper.readTree(url);
            return rootNode.path(DATA).get(0);
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