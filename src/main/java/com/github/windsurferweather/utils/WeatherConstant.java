package com.github.windsurferweather.utils;

import java.util.Map;

public class WeatherConstant {

    public static final String API_KEY = "273c07c4d603455cabd9f94cb58b526c";
    public static final String API_URL = "http://api.weatherbit.io/v2.0/forecast/daily?city=%s&country=%s&valid_date=%s&key=%s";

    public static final int MIN_TEMP = 5;
    public static final int MAX_TEMP = 35;
    public static final int MIN_WIND = 5;
    public static final int MAX_WIND = 18;

    public static final String DATE_REGEX = "((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";

    public static Map<String, String> createLocations() {
        return Map.of(
                "Jastarnia", "PL",
                "Bridgetown", "BB",
                "Fortaleza", "BR",
                "Pissouri", "CY",
                "Le Mont", "CH"
        );
    }
}
