package com.github.windurferweather.weather;

import com.github.windurferweather.config.ConfigurationClient;
import com.github.windurferweather.weather.dto.WeatherResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class WeatherServiceImplTest {

    private final WeatherClient weatherClient = new WeatherClient(new ConfigurationClient());
    private final WeatherServiceImpl weatherService = new WeatherServiceImpl(weatherClient);
    @Test
    public void shouldReturnTheBestLocationWithRangeSuitableCondition(){

        WeatherResponseDto weatherData = WeatherResponseDto.builder()
                .date("2023-03-05")
                .city_name("Warsaw")
                .temp(22.5)
                .wind_spd(10.0)
                .build();

        String locationExpected = weatherService.readWindsurfingLocation(weatherData.getDate()).getCity_name();

        Mockito.when(weatherService.readWindsurfingLocation(Mockito.anyString())).then(p -> !weatherData.getDate().isEmpty());

        Assertions.assertEquals(locationExpected, "Fortaleza");
    }

}