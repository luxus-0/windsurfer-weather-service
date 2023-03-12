package com.github.windsurferweather.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WeatherReportDto(String city, String country, double lat, double lon, LocalDateTime start,
                               LocalDateTime end, Double temperature, Double windSpeed) {
}
