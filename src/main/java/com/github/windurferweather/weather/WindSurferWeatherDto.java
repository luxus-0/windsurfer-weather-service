package com.github.windurferweather.weather;

import lombok.Builder;

@Builder
public record WindSurferWeatherDto(String city, String country, double windSpeed, double temperature) {
}
