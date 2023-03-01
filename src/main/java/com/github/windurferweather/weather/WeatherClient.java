package com.github.windurferweather.weather;

import com.github.windurferweather.weather.dto.WeatherResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static com.github.windurferweather.weather.WeatherConstant.*;

@Service
class WeatherClient {

 private final RestTemplate restTemplate = new RestTemplate();

    WeatherResponseDto getForecastWeather(String city, String country, String date) {

        return restTemplate.getForObject(ENDPOINT +
                "?city=" + city +
                "&country=" + country +
                "&valid_date=" + date +
                "&key=" + API_KEY, WeatherResponseDto.class, city, country, date);
    }
}
