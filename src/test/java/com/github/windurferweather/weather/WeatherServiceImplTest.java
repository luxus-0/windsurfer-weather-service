package com.github.windurferweather.weather;

import com.github.windurferweather.weather.dto.WeatherResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;

class WeatherServiceImplTest {
    private final WeatherClient weatherClient = new WeatherClient();

    private WeatherRepository weatherRepository;
    private final WeatherServiceImpl weatherService = new WeatherServiceImpl(weatherClient, weatherRepository);

    WeatherServiceImplTest(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }


    @Test
    @DisplayName("Should return incorrect city when user gave date")
    public void shouldReturnIncorrectCityWhenUserGaveDate(){

        WeatherResponseDto weatherData = WeatherResponseDto.builder()
                .date("2023-03-05")
                .city_name("Warsaw")
                .build();

        WeatherResponseDto weatherExcepted = weatherService.readTheBestLocationForWindsurfing(weatherData.getDate());

        Assertions.assertNotEquals(weatherExcepted.getCity_name(), "Fortaleza");
    }

    @Test
    @DisplayName("Should return correct country code when user gave date")
    public void shouldReturnCorrectCountryCodeWhenUserGaveDate(){

        String date = "2023-01-03";

        String country_code_excepted = weatherService.readTheBestLocationForWindsurfing(date).getCountry_code();

        Assertions.assertEquals(country_code_excepted, "BB");
    }

    @Test
    @DisplayName("Should return incorrect city and country when user gave date")
    public void shouldReturnInCorrectCityAndCountryWhenUserGaveDate(){

        String date = "2023-01-03";

        WeatherResponseDto weatherExpected = weatherService.readTheBestLocationForWindsurfing(date);
        String city_excepted = weatherExpected.getCity_name();
        String country_code_excepted = weatherExpected.getCountry_code();

        Assertions.assertNotEquals(city_excepted, "Jastarnia");
        Assertions.assertNotEquals(country_code_excepted, "JS");
    }

    @Test
    @DisplayName("Should return correct city and country when user gave today date")
    public void shouldReturnCorrectCityAndCountryWhenUserGaveTodayDate(){

        String date = LocalDateTime.now(Clock.systemUTC()).toString();

        WeatherResponseDto weatherExpected = weatherService.readTheBestLocationForWindsurfing(date);
        String cityExpected = weatherExpected.getCity_name();
        String countryCodeExpected = weatherExpected.getCountry_code();

        Assertions.assertEquals(cityExpected, "Bridgetown");
        Assertions.assertEquals(countryCodeExpected, "BB");
    }
}