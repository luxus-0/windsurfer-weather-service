package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.github.windurferweather.weather.DateValid.isValid;
import static com.github.windurferweather.weather.WeatherUtils.*;

@Log4j2
@Service
class WeatherForecastImpl implements WeatherForecastService {

    private final RestTemplate restTemplate;
    private final Environment environment;
    @Value("#{'${list.of.cities}'.split(',')}")
    private List<String> cities;

    WeatherForecastImpl(RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    @Override
    public WeatherResponse getWeatherForecastByDate(LocalDate date) {
        String weather_url = environment.getProperty("weather.api.url");
        String key = environment.getProperty("weather.api.key");
        for (String city : cities) {
            Objects.requireNonNull(weather_url);
            String weather_api = String.format(weather_url, city, key);
            WeatherResponse weatherResponse = restTemplate.getForObject(weather_api, WeatherResponse.class);
            List<WeatherResponse> weatherForecastData = new ArrayList<>();
            weatherForecastData.add(weatherResponse);

            String windSpeed = weatherForecastData.get(Integer.parseInt(WIND_SPEED_KEY)).toString();
            String temp = weatherForecastData.get(Integer.parseInt(TEMP_KEY)).toString();
            String actualDate = weatherForecastData.get(Integer.parseInt(VALID_DATE_KEY)).toString();

            isValid(actualDate);

            double wind_speed = Double.parseDouble(windSpeed);
            double temperature = Double.parseDouble(temp);

            Stream.of(windSpeed, temp, city)
                    .filter(checkWeather -> wind_speed > 5 && wind_speed < 18
                            && temperature > 5 && temperature < 35)
                    .findAny()
                    .ifPresent(checkWeather -> weatherForecastData.add(getWeather(city, wind_speed, temperature)));

            List<CityResponse> bestLocations = new ArrayList<>();
            bestLocations.add(CityResponse.builder()
                    .bestLocFactor(getBestLocForSurfer(wind_speed, temperature))
                    .build());

            log.info("Best location for surfer is: " + bestLocations);

            return getWeather(city, wind_speed, temperature);
        }
        return null;
    }

    private static float getBestLocForSurfer(double wind_speed, double temperature) {
        return (float) ((wind_speed * 3) + temperature);
    }

    private static WeatherResponse getWeather(String city, double wind_speed, double temperature) {
        return WeatherResponse.builder()
                .windSpeed(wind_speed)
                .avgTemp(temperature)
                .city(create(city))
                .build();
    }

    private static CityResponse create(String city) {
        return CityResponse.builder()
                .city(city)
                .build();
    }
}