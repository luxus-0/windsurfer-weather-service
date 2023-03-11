package com.github.windsurferweather.service;

import com.github.windsurferweather.controller.WeatherController;
import com.github.windsurferweather.exception.WeatherClientNotFoundException;
import com.github.windsurferweather.model.Data;
import com.github.windsurferweather.model.Weather;
import com.github.windsurferweather.restClient.WeatherClient;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.github.windsurferweather.utils.Tools.getToday;
import static com.github.windsurferweather.utils.WeatherConstant.*;
import static com.github.windsurferweather.utils.WeatherConstantUnitTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class WeatherServiceTest {
    @InjectMocks
    private WeatherClient weatherClient;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private WeatherService weatherService;

    WeatherServiceTest() {
    }

    @Test
    void shouldReturnForecastWeatherWhenLocationIsCorrect(){
        String city = "Jastarnia";
        String country = "pl";

        Weather forecastWeather = weatherClient.getForecastWeather(city, country, getToday());

        assertThat(forecastWeather).isNotNull();
    }

    @Test
    void shouldReturnBadRequestWhenDateFormatIsWrong() {

        String date = "2000-300-400";

        WeatherController weatherController = new WeatherController(weatherService);
        ResponseEntity<?> weatherResponse = weatherController.getWeather(date);

        assertThat(weatherResponse.getBody()).isNull();
        assertThat(weatherResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldReturnBadRequestWhenDateIsEmpty() {
        String date = "";

        WeatherController weatherController = new WeatherController(weatherService);
        ResponseEntity<?> weatherResponse = weatherController.getWeather(date);

       assertThat(weatherResponse.getBody()).isEqualTo("Niepoprawna data, proszę o datę w formacie yyyy-MM-dd");
       assertThat(weatherResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldReturnOKWhenDateIsCorrect() {
        String today = getToday();

        WeatherController weatherController = new WeatherController(weatherService);

        ResponseEntity<?> weatherResponse = weatherController.getWeather(today);

        assertThat(today).isNotNull();
        assertThat(weatherResponse).isNotNull();
        assertThat(weatherResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnFalseWhenCityAndCountryIsNotCorrect(){

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
    void shouldReturnIncorrectWindSpeedWhenGiveLocationAndDate() {

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

    @Test
    void shouldReturnIncorrectMinAndMaxTemperatureWhenGiveLocationAndDate() {

        String date = "2023-03-10";
        String city = "Jastarnia";
        String country = "Poland";
        String lat = "13.0969";
        String lon = "59.6145";

        Data expectedData = new Data(35.0, 25.0, 10.4, date);

        Weather expectedWeather = new Weather(city, country, lat, lon, List.of(expectedData));

        Weather actualWeather = weatherClient.getForecastWeather(city, country, date);

        double actualMinimumTemperature = getActualMinTemperature(actualWeather);
        double actualMaximumTemperature = getActualMaxTemperature(actualWeather);

        double expectedMinimumTemperature = getExpectedMinTemperature(expectedWeather);
        double expectedMaximumTemperature = getExpectedMaxTemperature(expectedWeather);

        assertThat(actualWeather).isNotNull();
        assertThat(expectedWeather).isNotNull();
        assertThat(expectedData).isNotNull();
        assertThat(actualMinimumTemperature).isNotEqualTo(expectedMinimumTemperature);
        assertThat(actualMaximumTemperature).isNotEqualTo(expectedMaximumTemperature);
    }

    @Test
    void shouldReturnCorrectMinAndMaxTemperatureWhenGiveLocationAndDate() {

        String date = "2023-02-17";
        String city = "Fortaleza";
        String country = "Brazil";
        String lat = "-3.7319";
        String lon = " -38.5267";

        Data expectedData = new Data(35.0, 20.0, 15, date);

        Weather expectedWeather = new Weather(city, country, lat, lon, List.of(expectedData));

        Weather actualWeather = weatherClient.getForecastWeather(city, country, date);

        double actualMinimumTemperature = getActualMinTemperature(actualWeather);
        double actualMaximumTemperature = getActualMaxTemperature(actualWeather);

        assertThat(actualWeather).isNotNull();
        assertThat(expectedWeather).isNotNull();
        assertThat(expectedData).isNotNull();
        assertThat(actualMinimumTemperature).isEqualTo(24.7);
        assertThat(actualMaximumTemperature).isEqualTo(28.7);
    }

    @Test
    public void shouldReturnErrorWhenGaveIncorrectLocation() {

        String day = "2023-03-20";
        String city = "Warsaw";
        String country = "Poland";

        WeatherClient weatherClient = mock(WeatherClient.class);

        when(weatherClient.getForecastWeather(city, country, day))
                .thenThrow(new WeatherClientNotFoundException(WEATHER_MESSAGE));
        }
}