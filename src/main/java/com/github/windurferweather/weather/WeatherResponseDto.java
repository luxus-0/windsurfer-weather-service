package com.github.windurferweather.weather;

import lombok.Builder;

@Builder
public record WeatherResponseDto(String city, String country, double windSpeed, double temp, String date) {
}
