package com.github.windurferweather.weather;

import com.github.windurferweather.weather.dto.WeatherResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;

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
    public void shouldReturnCorrectLocationWhenUserGaveCountryCodeWithConcreteDate(){

        String date = "2023-01-03";

        WeatherResponseDto weatherExpected = weatherService.readTheBestLocationForWindsurfing(date);
        String city_excepted = weatherExpected.getCity_name();
        String country_code_excepted = weatherExpected.getCountry_code();

        Assertions.assertEquals(city_excepted, "Bridgetown");
        Assertions.assertEquals(country_code_excepted, "BB");
    }

    @Test
    public void shouldReturnCorrectLocationWhenUserGaveLocationWithDate(){

        String date = LocalDateTime.now(Clock.systemUTC()).toString();

        WeatherResponseDto weatherExpected = weatherService.readTheBestLocationForWindsurfing(date);
        String cityExpected = weatherExpected.getCity_name();
        String countryCodeExpected = weatherExpected.getCountry_code();

        Assertions.assertEquals(cityExpected, "Bridgetown");
        Assertions.assertEquals(countryCodeExpected, "BB");
    }
}