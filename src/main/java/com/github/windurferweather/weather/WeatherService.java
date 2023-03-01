package com.github.windurferweather.weather;

import com.github.windurferweather.weather.dto.WeatherResponseDto;

public interface WeatherService {
    WeatherResponseDto readTheBestLocationForWindsurfing(String date) throws Exception;
}
