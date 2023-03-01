package com.github.windurferweather.weather;

import com.github.windurferweather.weather.dto.WeatherResponseDto;

public interface WeatherService {
    WeatherResponseDto readWindsurfingLocation(String date) throws Exception;
}
