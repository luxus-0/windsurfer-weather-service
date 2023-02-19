package com.github.windurferweather.weather;

public interface WeatherService {
    WeatherResponseDto readWindsurfingLocation(String date) throws Exception;
}
