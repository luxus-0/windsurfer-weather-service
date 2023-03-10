package com.github.windsurferweather.utils;

import com.github.windsurferweather.model.Data;
import com.github.windsurferweather.model.Weather;

import java.util.List;
import java.util.stream.Stream;

public class WeatherConstantUnitTest {
    public static double getExpectedMaxTemperature(Weather expectedWeather) {
        return Stream.of(expectedWeather)
                .map(Weather::getData)
                .flatMap(List::stream)
                .findAny()
                .map(Data::getMaxTemperature)
                .orElse(0d);
    }

    public static double getExpectedMinTemperature(Weather expectedWeather) {
        return expectedWeather.getData().stream()
                .map(Data::getMinTemperature).findAny()
                .orElseThrow();
    }

    public static double getActualMaxTemperature(Weather actualWeather) {
        return actualWeather.getData().stream()
                .mapToDouble(Data::getMaxTemperature)
                .findAny()
                .orElse(-111111);
    }

    public static double getActualMinTemperature(Weather actualWeather) {
        return actualWeather.getData().stream()
                .mapToDouble(Data::getMinTemperature)
                .iterator()
                .next();
    }

    public static double getExpectedWindSpeed(Weather expectedWeather) {
        return expectedWeather.getData().stream()
                .mapToDouble(Data::getWindSpeed)
                .findAny()
                .orElse(0d);
    }

    public static Double getActualWindSpeed(Weather actualWeather) {
        return actualWeather.getData().stream()
                .map(Data::getWindSpeed).findAny()
                .orElse(0d);
    }

}
