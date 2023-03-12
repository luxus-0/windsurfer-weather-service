package com.github.windsurferweather.service;

import com.github.windsurferweather.model.Weather;
import com.github.windsurferweather.restClient.WeatherClient;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.github.windsurferweather.utils.WeatherConstantUnitTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class WeatherServiceParameterizedTest {
    @Autowired
    private WeatherClient weatherClient;

    @Autowired
    private WeatherService weatherService;

    @ParameterizedTest
    @CsvSource({
            "Jastarnia, PL, 2023-03-13, 7.0, 10.0, 2.0",
            "Bridgetown, BB, 2023-03-15, 12.0, 28.0, 4.0",
            "Fortaleza, BR, 2023-03-17, 16.0, 30.0, 6.0",
            "Pissouri, CY, 2023-03-19, 10.0, 21.0, 8.0"
    })
    void shouldReturnNotCorrectTemperatureAndWindSpeed(String expectedCity, String expectedCountry, String expectedDate, double expectedMinTemp, double expectedMaxTemp, double expectedWindSpeed){

        Weather actualWeather = weatherClient.getForecastWeather(expectedCity,expectedCountry, expectedDate);

         String actualCity = actualWeather.getCityName();
         String actualCountry = actualWeather.getCountryCode();
         Double actualMinTemperature = getActualMinTemperature(actualWeather);
         Double actualMaxTemperature = getActualMaxTemperature(actualWeather);
         Double actualWindSpeed = getActualWindSpeed(actualWeather);

         assertThat(actualWeather).isNotNull();
         assertThat(actualCity).isEqualTo(expectedCity);
         assertThat(actualCountry).isEqualTo(expectedCountry);
         assertThat(actualMinTemperature).isNotEqualTo(expectedMinTemp);
         assertThat(actualMaxTemperature).isNotEqualTo(expectedMaxTemp);
         assertThat(actualWindSpeed).isNotEqualTo(expectedWindSpeed);
    }
    @ParameterizedTest
    @CsvSource({"2023-03-13, 7.0, 10.0, 0.0",
                "2023-03-15, 12.0, 9.0, 0.0",
                "2023-03-17, 16.0, 4.0, 0.0",
                "2023-03-19, 10.0, 21.0, 0.0"
    })
    void shouldReturnFalseWhenTempAndWindSpeedIsZeroOrNotCorrect(String expectedDate, double expectedMinTemp, double expectedMaxTemp, double expectedWindSpeed){

        List<Weather> actualWeather = weatherService.getWeatherForAllCountries(expectedDate);

        double windSpeed = readWindSpeed(actualWeather);
        double minTemp = readMinTemperature(actualWeather);
        double maxTemp = readMaxTemperature(actualWeather);

        assertThat(actualWeather).isNotNull();
        assertNotEquals(minTemp, expectedMinTemp);
        assertNotEquals(maxTemp, expectedMaxTemp);
        assertNotEquals(windSpeed, expectedWindSpeed);
        assertEquals(0, expectedWindSpeed);
    }
}
