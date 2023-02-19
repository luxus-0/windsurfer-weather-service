package com.github.windurferweather.weather;

import lombok.Builder;

@Builder
public record WeatherResponseDto(LocationDto locationDto, WeatherConditionDto weatherConditionDto, String date) {
}
