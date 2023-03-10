package com.github.windsurferweather.service;

import com.github.windsurferweather.model.Data;
import com.github.windsurferweather.model.Weather;
import com.github.windsurferweather.restClient.WeatherClient;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.github.windsurferweather.utils.Tools.getToday;
import static com.github.windsurferweather.utils.WeatherConstant.API_KEY;
import static com.github.windsurferweather.utils.WeatherConstant.API_URL;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class WeatherServiceTest {
    @InjectMocks
    private WeatherClient weatherClient;
    @Mock
    private RestTemplate restTemplate;

    WeatherServiceTest() {
    }

    @Test
    void shouldReturnForecastWeather(){
        String city = "Jastarnia";
        String country = "pl";

        Weather forecastWeather = weatherClient.getForecastWeather(city, country, getToday());

        assertThat(forecastWeather).isNotNull();
    }

    @Test
    void shouldReturnForecastWeatherWhenIsIncorrectDate() {
        String city = "Bridgetown";
        String country = "BB";
        String today = now().minus(2, ChronoUnit.DAYS).toString();

        Weather forecastWeather = weatherClient.getForecastWeather(city, country, today);

        assertThat(forecastWeather).isNotNull();
        assertThat(today).isNotEqualTo(getToday());
    }

    @Test
    void shouldReturnForecastWeatherWhenIsCorrectDate() {
        String city = "Bridgetown";
        String country = "BB";
        String today = String.valueOf(LocalDate.now());

        Weather forecastWeather = weatherClient.getForecastWeather(city, country, today);

        assertThat(forecastWeather).isNotNull();
        assertThat(today).isEqualTo(getToday());
    }

    @Test
    void shouldReturnForecastWeatherWhenCityAndCountryIsInCorrect(){

        Weather forecastWeather = weatherClient.getForecastWeather("Warsaw", "PL", getToday());

        assertThat(forecastWeather).isNotNull();
        assertThat(forecastWeather.getCityName()).isNotEqualTo("Fortaleza");
        assertThat(forecastWeather.getCountryCode()).isNotEqualTo("BR");
    }

    @Test
    void shouldReturnForecastWeatherWhenCityAndCountryIsCorrect(){

        Weather forecastWeather = weatherClient.getForecastWeather("Warsaw", "PL", getToday());

        assertThat(forecastWeather).isNotNull();
        assertThat(forecastWeather.getCityName())
                .isNotEqualTo("Fortaleza")
                .isNotEqualTo("Bridgetown")
                .isNotEqualTo("Pissouri")
                .isNotEqualTo("Le Mont");
    }

    @Test
    void shouldReturnOKWhenCityAndCountryAndDateIsCorrect() {
        String city = "Jastarnia";
        String country = "PL";
        String date = getToday();
        String url = String.format(API_URL, city, country, date, API_KEY);

        Weather weather = new Weather();
        weather.setCityName(city);
        weather.setCountryCode(country);

        when(restTemplate.getForEntity(url, Weather.class))
                .thenReturn(new ResponseEntity<>(weather, HttpStatus.OK));

        Weather result = weatherClient.getForecastWeather(city, country, date);

        assertEquals(weather.getCityName(), result.getCityName());
        assertEquals(weather.getCountryCode(), result.getCountryCode());
    }

    @Test
    void shouldReturnBadRequestWhenCityAndCountryIsIncorrect() {

        String city = "Warsaw";
        String country = "PL";
        String date = "2023-03-07";
        String url = String.format(API_URL, city, country, date, API_KEY);

        when(restTemplate.getForObject(url, Weather.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid Parameters supplied."));
    }

    @Test
    void shouldReturnBadRequestWhenCityAndCountryIsEmpty() {

        String city = "";
        String country = "";
        String date = "2023-03-07";
        String url = String.format(API_URL, city, country, date, API_KEY);

        when(restTemplate.getForObject(url, Weather.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid Parameters supplied."));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidDate() {

        String city = "Barbados";
        String country = "Brasil";
        String date = "";
        String url = String.format(API_URL, city, country, date, API_KEY);

        when(restTemplate.getForObject(url, Weather.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid Parameters supplied."));
    }

    @Test
    void shouldReturnIncorrectWindSpeedWhenGiveLocalizationAndDate() {

        String date = "2023-03-10";
        String city = "Bridgetown";
        String country = "Barbados";
        String lat = "13.0969";
        String lon = "59.6145";

        Data expectedData = new Data(35.0, 25.0, 10.4, date);

        Weather expectedWeather = new Weather(city, country, lat, lon, List.of(expectedData));

        Weather actualWeather = weatherClient.getForecastWeather(city, country, date);

        double actualWindSpeed = getActualWindSpeed(actualWeather);
        double expectedWindSpeed = getExpectedWindSpeed(expectedWeather);

        assertThat(actualWeather).isNotNull();
        assertThat(expectedWeather).isNotNull();
        assertThat(actualWindSpeed).isNotEqualTo(expectedWindSpeed);
    }

    private static double getExpectedWindSpeed(Weather expectedWeather) {
        return expectedWeather.getData().stream()
                .mapToDouble(Data::getWindSpeed)
                .findAny()
                .orElse(0d);
    }

    private static Double getActualWindSpeed(Weather actualWeather) {
        return actualWeather.getData().stream()
                .map(Data::getWindSpeed).findAny()
                .orElse(0d);
    }

}