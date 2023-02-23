package com.github.windurferweather.weather;

import java.util.List;

public class AirQualityMapper {
    static AirQuality mapToAirQuality(AirQualityDto airQualityDto) {

        Data data = new Data(airQualityDto.getAqi());

        return AirQuality.builder()
                .city(airQualityDto.getCity())
                .data(List.of(data))
                .pm10(airQualityDto.getPm10())
                .pm25(airQualityDto.getPm25())
                .build();
    }
}
