package com.github.windurferweather.weather;

import java.time.LocalDate;

public interface WeatherForecastService {
    WeatherResponse getWeatherForecastByDate(LocalDate date);
}
