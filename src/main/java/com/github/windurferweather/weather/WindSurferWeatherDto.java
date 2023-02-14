package com.github.windurferweather.weather;

import lombok.Builder;

@Builder
public record WindSurferWeatherDto(LocalizationDto localizationDto, double windSpeed, double temperature) {
}
