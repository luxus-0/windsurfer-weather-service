package com.github.windsurferweather.utils;

import com.github.windsurferweather.model.Data;

import static com.github.windsurferweather.utils.WeatherConstant.*;

public class WeatherValidation {
    public static boolean validateData(Data data) {
        double windSpeed = data.getWindSpeed();
        double maxTemperature = data.getMaxTemperature();
        double minTemperature = data.getMinTemperature();
        return windSpeed > MAX_WIND || windSpeed < MIN_WIND || maxTemperature > MAX_TEMP || minTemperature < MIN_TEMP;
    }
}
