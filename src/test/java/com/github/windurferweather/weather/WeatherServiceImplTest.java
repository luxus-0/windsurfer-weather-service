package com.github.windurferweather.weather;

import com.github.windurferweather.weather.dto.WeatherResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class WeatherServiceImplTest {
    private final WeatherClient weatherClient = new WeatherClient();
    private final WeatherServiceImpl weatherService = new WeatherServiceImpl(weatherClient);


    @Test
    public void shouldReturnTheBestLocationWithRangeSuitableCondition(){

        WeatherResponseDto weatherData = WeatherResponseDto.builder()
                .date("2023-03-05")
                .city_name("Warsaw")
                .temp(22.5)
                .wind_spd(10.0)
                .build();

        WeatherResponseDto weatherExcepted = weatherService.readWindsurfingLocation(weatherData.getDate());

        WeatherResponseDto weatherActual = weatherService.readWindsurfingLocation("2022-03-07");

        Assertions.assertEquals(weatherExcepted.getDate(), weatherActual.getDate());
    }

}