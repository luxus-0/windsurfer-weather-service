package com.github.windurferweather.weather;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WeatherApi {

    private final WindSurferServiceImpl windSurferServiceImpl;

    WeatherApi(WindSurferServiceImpl windSurferServiceImpl) {
        this.windSurferServiceImpl = windSurferServiceImpl;
    }

    @GetMapping("/weather/{date}")
    WindSurferWeatherDto readWindSurferWeatherLocalizedByDate(@PathVariable String date){
        return windSurferServiceImpl.readWindSurfingLocationByDate(date);
    }
}
