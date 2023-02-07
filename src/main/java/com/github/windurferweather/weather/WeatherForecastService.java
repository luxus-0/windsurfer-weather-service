package com.github.windurferweather.weather;

public interface WeatherForecastService {
    WeatherResponseDto retrieveWeatherForecastByDate(String date);
}
