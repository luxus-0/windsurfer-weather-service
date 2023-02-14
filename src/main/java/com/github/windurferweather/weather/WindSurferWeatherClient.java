package com.github.windurferweather.weather;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.*;

@Service
class WindSurferWeatherClient {

    private final RestTemplate restTemplate = new RestTemplate();

    WindSurferWeatherDto readWindSurfingByLocation(String city, String country) {
        String url = WEATHER_URL + "daily?" + "city=" + city +"&country=" +country+ "&key=" + API_KEY;
        return restTemplate.getForObject(url, WindSurferWeatherDto.class);
    }

    WindSurferWeatherDto readWindSurfingByDate(String datetime) {
        String url = WEATHER_URL + "daily?" + "date=" + datetime +"&valid_date=" +datetime+ "&key=" + API_KEY;
        return restTemplate.getForObject(url, WindSurferWeatherDto.class);
    }
}
