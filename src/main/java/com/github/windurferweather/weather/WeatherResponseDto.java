package com.github.windurferweather.weather;

public record WeatherResponseDto(String city, String country, double temperature, double windSpeed) {
}
