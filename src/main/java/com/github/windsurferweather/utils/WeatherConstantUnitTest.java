package com.github.windsurferweather.utils;

import com.github.windsurferweather.model.Data;
import com.github.windsurferweather.model.Weather;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
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

    public static double getActualWindSpeed(Weather expectedWeather) {
        return expectedWeather.getData().stream()
                .mapToDouble(Data::getWindSpeed)
                .findAny()
                .orElse(0d);
    }

    public static Double readWindSpeed(List<Weather> actualWeather) {
        return actualWeather.stream()
                .map(Weather::getData)
                .flatMap(Collection::stream)
                .map(Data::getWindSpeed)
                .findAny()
                .orElseThrow();
    }
    public static Double readMaxTemperature(List<Weather> actualWeather) {
        return actualWeather.listIterator()
                .next()
                .getData()
                .stream()
                .mapToDouble(Data::getMaxTemperature)
                .iterator()
                .next();
    }

    public static Double readMinTemperature(List<Weather> actualWeathers) {
        for(Weather weather : actualWeathers){
            ListIterator<Data> weatherIterator = weather.getData().listIterator();
            if(weatherIterator.hasNext()){
                return weatherIterator.next().getMinTemperature();
            }
        }
        return 0d;
    }
}
