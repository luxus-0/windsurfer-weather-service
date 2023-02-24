package com.github.windurferweather.weather;

import java.util.List;

public class AirQualityMapper {
    static AirQuality mapToAirQuality(AirQualityDto airQualityDto) {

        Data data = getData(airQualityDto);

        return AirQuality.builder()
                .city(airQualityDto.getCity())
                .data(data)
                .pm10(airQualityDto.getPm10())
                .pm25(airQualityDto.getPm25())
                .build();
    }
    static Data getData(AirQualityDto airQualityDto) {
        return new Data(airQualityDto.getAqi());
    }
}
