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
    private int pm25;
    private int pm10;
}
