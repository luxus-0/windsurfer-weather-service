package com.github.windurferweather.weather;

public record WeatherConditionDto(String location, double windSpeed, double temperature, String date) {
}
