package com.github.windurferweather.weather;

import com.github.windurferweather.weather.dto.LocationDto;
import com.github.windurferweather.weather.dto.WeatherResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;

class WeatherServiceImplTest {
    private final WeatherClient weatherClient = new WeatherClient();
    private final WeatherServiceImpl weatherService = new WeatherServiceImpl(weatherClient);

    @Test
    @DisplayName("Should return incorrect city when user gave date")
    public void shouldReturnIncorrectCityWhenUserGaveDate(){

        WeatherResponseDto weatherData = WeatherResponseDto.builder()
                .date("2023-03-05")
                .locationDto(new LocationDto("Warsaw","",0,0))
                .build();

        WeatherResponseDto weatherExcepted = weatherService.readTheBestLocationForWindsurfing(weatherData.getDate());

        Assertions.assertNotEquals(weatherExcepted.getLocationDto().city(), "Fortaleza");
    }

    @Test
    @DisplayName("Should return correct country code when user gave date")
    public void shouldReturnCorrectCountryCodeWhenUserGaveDate(){

        String date = "2023-01-03";

        String country_code_excepted = weatherService.readTheBestLocationForWindsurfing(date)
                .getLocationDto().city();

        Assertions.assertEquals(country_code_excepted, "BB");
    }

    @Test
    @DisplayName("Should return incorrect city and country when user gave date")
    public void shouldReturnInCorrectCityAndCountryWhenUserGaveDate(){

        String date = "2023-01-03";

        WeatherResponseDto weatherExpected = weatherService.readTheBestLocationForWindsurfing(date);
        String cityExpected = weatherExpected.getLocationDto().city();
        String countryCodeExpected = weatherExpected.getLocationDto().country();
        Assertions.assertNotEquals(cityExpected, "Jastarnia");
        Assertions.assertNotEquals(countryCodeExpected, "JS");
    }

    @Test
    @DisplayName("Should return correct city and country when user gave today date")
    public void shouldReturnCorrectCityAndCountryWhenUserGaveTodayDate(){

        String date = LocalDateTime.now(Clock.systemUTC()).toString();

        WeatherResponseDto weatherExpected = weatherService.readTheBestLocationForWindsurfing(date);
        String cityExpected = weatherExpected.getLocationDto().city();
        String countryCodeExpected = weatherExpected.getLocationDto().country();

        Assertions.assertEquals(cityExpected, "Bridgetown");
        Assertions.assertEquals(countryCodeExpected, "BB");
    }
}