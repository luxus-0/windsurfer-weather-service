package com.github.windurferweather.weather;

import com.github.windurferweather.weather.dto.LocationDto;
import com.github.windurferweather.weather.dto.WeatherResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class WeatherServiceImplJunitTest {
    private final WeatherClient weatherClient = new WeatherClient();
    private final WeatherServiceImpl weatherService = new WeatherServiceImpl(weatherClient);
    private final MockMvc mockMvc;

    WeatherServiceImplJunitTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }


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

        String countryExcepted = weatherService.readTheBestLocationForWindsurfing(date)
                .getLocationDto().city();

        Assertions.assertEquals(countryExcepted, "Bridgetown");
    }

    @Test
    @DisplayName("Should return incorrect city and country when user gave date")
    public void shouldReturnInCorrectCityAndCountryWhenUserGaveDate(){

        String date = "2023-01-03";

        WeatherResponseDto weatherExpected = weatherService.readTheBestLocationForWindsurfing(date);
        String cityExpected = weatherExpected.getLocationDto().city();
        String countryExpected = weatherExpected.getLocationDto().country();
        Assertions.assertNotEquals(cityExpected, "Jastarnia");
        Assertions.assertNotEquals(countryExpected, "JS");
    }

    @Test
    @DisplayName("Should return correct city and country when user gave today date")
    public void shouldReturnCorrectCityAndCountryWhenUserGaveTodayDate(){

        String date = "2023-01-03";

        WeatherResponseDto weatherExpected = weatherService.readTheBestLocationForWindsurfing(date);
        String cityExpected = weatherExpected.getLocationDto().city();
        String countryExpected = weatherExpected.getLocationDto().country();

        Assertions.assertEquals(cityExpected, "Bridgetown");
        Assertions.assertEquals(countryExpected, "Barbados");
    }
}