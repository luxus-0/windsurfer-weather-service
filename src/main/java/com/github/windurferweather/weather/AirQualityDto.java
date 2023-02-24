package com.github.windurferweather.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AirQualityDto {
    private String city;
    private int aqi;
    private double pm25;
    private double pm10;
}
