package com.github.windsurferweather.restClient;

import com.github.windsurferweather.model.Weather;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.github.windsurferweather.utils.WeatherConstant.API_KEY;
import static com.github.windsurferweather.utils.WeatherConstant.API_URL;

@Service
public
class WeatherClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public Weather getForecastWeather(String city, String country, String date) {
        String url = String.format(API_URL, city, country, date, API_KEY);
        return restTemplate.getForObject(url, Weather.class);
    }
}
