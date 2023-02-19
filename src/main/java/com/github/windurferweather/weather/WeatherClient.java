package com.github.windurferweather.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.net.URL;

import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.API_KEY;
import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.WEATHER_URL;

@Service
@Log4j2
class WeatherClient {

    WeatherResponseDto getWeatherForLocation(String city, String country, String localDateTime) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        WeatherResponseDto weatherResponseDto = mapper.readValue(new URL(WEATHER_URL + "daily?city=" + city + "&country=" + country + "&datetime=" + localDateTime + "&key=" + API_KEY), WeatherResponseDto.class);

        double temperature = weatherResponseDto.temp();
       double windSpeed = weatherResponseDto.windSpeed();

        return WeatherResponseDto.builder()
                .city(city)
                .country(country)
                .windSpeed(windSpeed)
                .temp(temperature)
                .date(localDateTime)
                .build();
    }
}
