package com.github.windurferweather.weather;

import com.github.windurferweather.config.ConfigurationClient;
import org.springframework.stereotype.Service;

import static com.github.windurferweather.weather.AirQualityConstant.AIR_QUALITY_URL;

@Service
class AirQualityClient {
    private final ConfigurationClient client;

    AirQualityClient(ConfigurationClient client) {
        this.client = client;
    }

    AirQualityDto readAirQuality(String city) {
        return client.getRestTemplate()
                .getForObject(AIR_QUALITY_URL, AirQualityDto.class, city);
    }
}
