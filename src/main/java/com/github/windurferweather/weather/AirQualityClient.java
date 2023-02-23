package com.github.windurferweather.weather;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.github.windurferweather.weather.AirQualityConstant.AIR_QUALITY_URL;

@Service
class AirQualityClient {
    private final RestTemplate restTemplate;

    AirQualityClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    AirQualityDto readAirQuality(String city) {
        return restTemplate.getForObject(AIR_QUALITY_URL, AirQualityDto.class, city);
    }
}
