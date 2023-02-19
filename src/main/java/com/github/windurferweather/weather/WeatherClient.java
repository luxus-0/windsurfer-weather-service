package com.github.windurferweather.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

import static com.github.windurferweather.weather.WeatherConditionMessage.TEMPERATURE;
import static com.github.windurferweather.weather.WeatherConditionMessage.WIND_SPEED;
import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.API_KEY;
import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.WEATHER_URL;

@Service
@Log4j2
class WeatherClient {

    WeatherResponseDto getWeatherForLocation(String city, String country, String localDateTime) throws Exception {
        JsonNode json = convertToJsonWeather(city, country, localDateTime);

        double windSpeed = json.get(WIND_SPEED).asDouble();
        double temperature = json.get(TEMPERATURE).asDouble();

        WeatherConditionDto weatherConditionDto = new WeatherConditionDto(windSpeed, temperature);
        LocationDto location = new LocationDto(city, country);

        return WeatherResponseDto.builder()
                .weatherConditionDto(weatherConditionDto)
                .locationDto(location)
                .date(localDateTime)
                .build();
    }

    private JsonNode convertToJsonWeather(String city, String country, String date) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new URL(WEATHER_URL + "daily?city=" + city + "&country=" + country + "&datetime=" + date + "&key=" + API_KEY));
        JsonNode data = root.get("data");
        JsonNode day = data.get(0);
        log.info(day);
        return day;
    }
}
