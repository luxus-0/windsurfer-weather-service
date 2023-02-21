package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.github.windurferweather.weather.WeatherConstant.getUrl;

@Service
@Log4j2
class WeatherClient {

    WeatherResponseDto getForecastWeather(String city, String country_code) {
        RestTemplate restTemplate = new RestTemplate();
        String url = getUrl(city, country_code);
        return restTemplate.getForObject(url, WeatherResponseDto.class, city, country_code);
    }
}
