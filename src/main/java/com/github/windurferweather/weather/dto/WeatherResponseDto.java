package com.github.windurferweather.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponseDto {
        private LocationDto locationDto;
        private double wind_spd;
        private double temp;
        private String date;
}
