package com.github.windurferweather.weather;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class WeatherServiceImplTest {

    private final WeatherServiceImpl weatherService;

    WeatherServiceImplTest(WeatherServiceImpl weatherService) {
        this.weatherService = weatherService;
    }
    @Test
    public void shouldReturnTheBestLocationWithRangeSuitableCondition(){

        WeatherResponseDto weatherData = WeatherResponseDto.builder()
                .date("2023-03-05")
                .city_name("Warsaw")
                .temp(22.5)
                .wind_spd(10.0)
                .build();

        WeatherResponseDto weatherResponse = weatherService.readWindsurfingLocation(weatherData.getDate());

        Mockito.when(weatherService.readWindsurfingLocation(Mockito.anyString())).thenReturn(weatherData);

        Assertions.assertEquals(weatherResponse.getCity_name(), "Fortaleza");
    }

}