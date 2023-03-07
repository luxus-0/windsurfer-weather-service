package com.github.windsurferweather.restClient;

import com.github.windsurferweather.model.Weather;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import static com.github.windsurferweather.utils.Tools.getToday;
import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeatherClientTest {

   private final WeatherClient weatherClient = new WeatherClient();

    @Test
    void shouldReturnForecastWeather(){
        String city = "Jastarnia";
        String country = "pl";

        Weather forecastWeather = weatherClient.getForecastWeather(city, country, getToday());

        Assertions.assertThat(forecastWeather).isNotNull();
    }

    @Test
    void shouldReturnForecastWeatherWhenIncorrectDate(){
        String city = "Bridgetown";
        String country = "BB";
        String today = now().minus(2, ChronoUnit.DAYS).toString();

        Weather forecastWeather = weatherClient.getForecastWeather(city, country, today);

        Assertions.assertThat(forecastWeather).isNotNull();
        Assertions.assertThat(today).isNotEqualTo(getToday());
    }

    @Test
    void shouldReturnForecastWeatherWhenCorrectDate(){
        String city = "Bridgetown";
        String country = "BB";
        String today = String.valueOf(LocalDate.now());

        Weather forecastWeather = weatherClient.getForecastWeather(city, country, today);

        Assertions.assertThat(forecastWeather).isNotNull();
        Assertions.assertThat(today).isEqualTo(getToday());
    }

    @Test
    void shouldReturnForecastWeatherWhenCityAndCountryIsInCorrect(){

        Weather forecastWeather = weatherClient.getForecastWeather("Warsaw", "PL", getToday());

        Assertions.assertThat(forecastWeather).isNotNull();
        Assertions.assertThat(forecastWeather.getCityName()).isNotEqualTo("Fortaleza");
        Assertions.assertThat(forecastWeather.getCountryCode()).isNotEqualTo("BR");
    }

    @Test
    void shouldReturnForecastWeatherWhenCityAndCountryIsCorrect(){

        Weather forecastWeather = weatherClient.getForecastWeather("Warsaw", "PL", getToday());

        Assertions.assertThat(forecastWeather).isNotNull();
        Assertions.assertThat(forecastWeather.getCityName())
                .isNotEqualTo("Fortaleza")
                .isNotEqualTo("Bridgetown")
                .isNotEqualTo("Pissouri")
                .isNotEqualTo("Le Mont");
    }

    @Test
    void shouldThrowBadRequestWhenCityAndCountryIsIncorrect(){
        String city = "";
        String country = "";


        assertThrows(HttpClientErrorException.BadRequest.class,
                ()->  weatherClient.getForecastWeather(city,country, getToday()));
    }
}