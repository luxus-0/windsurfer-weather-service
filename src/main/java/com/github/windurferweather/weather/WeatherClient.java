package com.github.windurferweather.weather;

import com.github.windurferweather.config.ConfigurationClient;
import org.springframework.stereotype.Service;

import static com.github.windurferweather.weather.WeatherConstant.getUrl;

@Service
class WeatherClient {

    private final ConfigurationClient client;

    WeatherClient(ConfigurationClient client) {
        this.client = client;
    }

    WeatherResponseDto getForecastWeather(String city, String country_code) {
        String url = getUrl(city, country_code);
        return client.getRestTemplate().getForObject(url, WeatherResponseDto.class, city, country_code);
    }
}
