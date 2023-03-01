package com.github.windurferweather.weather;

import com.github.windurferweather.weather.dto.WeatherResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class WeatherServiceImplTest {
    private final WeatherClient weatherClient = new WeatherClient();
    private final WeatherServiceImpl weatherService = new WeatherServiceImpl(weatherClient);


    @Test
    public void shouldReturnIncorrectCityWhenUserGaveCityWithConcreteDate(){

        WeatherResponseDto weatherData = WeatherResponseDto.builder()
                .date("2023-03-05")
                .city_name("Warsaw")
                .build();

        WeatherResponseDto weatherExcepted = weatherService.readTheBestLocationForWindsurfing(weatherData.getDate());

        Assertions.assertNotEquals(weatherExcepted.getCity_name(), "Fortaleza");
    }

    @Test
    public void shouldReturnCorrectCountryCodeWhenUserGaveCountryCodeWithConcreteDate(){

        String date = "2023-01-03";

        String country_code_excepted = weatherService.readTheBestLocationForWindsurfing(date).getCountry_code();

        Assertions.assertEquals(country_code_excepted, "BB");
    }

    @Test
    public void shouldReturnIncorrectCountryCodeWhenUserGaveCountryCodeWithConcreteDate(){

        String date = "2023-01-03";

        String country_code_excepted = weatherService.readTheBestLocationForWindsurfing(date).getCountry_code();

        Assertions.assertNotEquals(country_code_excepted, "ZZ");
    }
}