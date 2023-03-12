package com.github.windsurferweather.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public record WeatherReportDto(String city, String country, double lat, double lon, LocalDateTime start,
                               LocalDateTime end, Double temperature, Double windSpeed) {
}
