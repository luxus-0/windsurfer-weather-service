package com.github.windurferweather.weather;

public interface WeatherService {
    WeatherConditionDto readWindsurfingLocation(String date) throws Exception;
}
