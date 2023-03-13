package com.github.windsurferweather.service;

import com.github.windsurferweather.model.Data;
import com.github.windsurferweather.model.Weather;
import com.github.windsurferweather.restClient.WeatherClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

import static com.github.windsurferweather.utils.WeatherConstant.DATE_REGEX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class WeatherServiceIntegrationTest {

    @Autowired
    private WeatherClient weatherClient;

    @Test
    public void shouldReturnTempAndWindSpeedMoreThanZeroWhenLocationAndDateIsCorrect() {
        String city = "Jastarnia";
        String date = "2023-03-15";
        String country_code = "PL";

        Weather weather = weatherClient.getForecastWeather(city, country_code, date);
        double maxTemp = weather.getData().stream().mapToDouble(Data::getMaxTemperature).findAny().orElse(0d);
        double minTemp = weather.getData().stream().mapToDouble(Data::getMaxTemperature).findAny().orElseThrow();
        double windSpeed = weather.getData().stream().mapToDouble(Data::getWindSpeed).findAny().orElse(0d);


        assertNotNull(weather);
        assertThat("Jastarnia").isEqualTo(weather.getCityName());
        assertEquals("", date, "2023-03-15");
        assertTrue(maxTemp > 0.0);
        assertTrue(minTemp > 0.0);
        assertTrue(windSpeed > 0);
    }

    @Test
    public void shouldReturnFalseDateWhenDateFormatIsIncorrect() {
        String city = "Jastarnia";
        String date = "20230-03-15";
        String country_code = "PL";

        Weather weather = weatherClient.getForecastWeather(city, country_code, "2023-03-10");

        assertNotNull(weather);
        assertThat(date).doesNotMatch(Pattern.compile(DATE_REGEX));
    }
}
