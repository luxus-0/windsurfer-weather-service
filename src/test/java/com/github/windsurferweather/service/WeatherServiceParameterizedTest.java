package com.github.windsurferweather.service;

import com.github.windsurferweather.model.Data;
import com.github.windsurferweather.model.Weather;
import com.github.windsurferweather.restClient.WeatherClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WeatherServiceParameterizedTest {
    @Autowired
    private WeatherClient weatherClient;

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

    private static Double getActualWindSpeed(Weather actualWeather) {
        return actualWeather.getData().stream()
                .map(Data::getWindSpeed)
                .findAny()
                .orElse(0d);
    }

    private static Double getActualMaxTemperature(Weather actualWeather) {
        return actualWeather.getData().stream()
                .map(Data::getMaxTemperature)
                .findAny()
                .orElseThrow();
    }

    private static Double getActualMinTemperature(Weather actualWeather) {
        return actualWeather.getData().stream()
                .map(Data::getMinTemperature)
                .findAny()
                .orElse(-1111d);
    }
}
