package com.github.windsurferweather.service;

import com.github.windsurferweather.model.BestWeather;
import com.github.windsurferweather.model.Data;
import com.github.windsurferweather.model.Weather;
import com.github.windsurferweather.restClient.WeatherClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.windsurferweather.utils.WeatherConstant.createLocations;
import static com.github.windsurferweather.utils.WeatherValidation.validateData;

@Service
public class WeatherService {

    private final WeatherClient weatherClient;

    public WeatherService(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public List<Weather> getWeatherForAllCountries(String date) {
        return createLocations().entrySet().stream()
                .map(location -> weatherClient.getForecastWeather(location.getKey(), location.getValue(), date)).toList();
    }

    public List<BestWeather> collectBestPlaceToSurfForEachDay(String date) {
        List<BestWeather> bestWeathers = new ArrayList<>();
        List<Weather> weatherForAllCountries = getWeatherForAllCountries(date);
        for (int i = 0; i < 16; i++) {
            Map<Double, BestWeather> weatherMap = new HashMap<>();
            for (Weather weather : weatherForAllCountries) {
                Data data = weather.getData().get(i);
                if (validateData(data))
                    weatherMap.put(-9999d, new BestWeather(data));
                else
                    weatherMap.put(calculatePoints(data), new BestWeather(weather.getCityName(), data));
            }
                bestWeathers.add(pickUpBestWeather(weatherMap));
        }
        return bestWeathers;
    }

    private BestWeather pickUpBestWeather(Map<Double, BestWeather> weatherMap) {
        Double max = weatherMap.keySet().stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElseThrow();
        return weatherMap.get(max);
    }

    private double calculatePoints(Data data) {
        if (validateData(data))
            return -99999;
        return (data.getWindSpeed() * 3) + ((data.getMinTemperature() + data.getMaxTemperature()) / 2);
    }
}