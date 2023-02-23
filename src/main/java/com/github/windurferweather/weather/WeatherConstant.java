package com.github.windurferweather.weather;

import static com.github.windurferweather.weather.WeatherEndpoint.ENDPOINT;

class WeatherConstant {

    public static final String API_KEY = "273c07c4d603455cabd9f94cb58b526c";
    public static final int MIN_TEMP = 3;
    public static final int MAX_TEMP = 35;
    public static final int MIN_WIND = 5;
    public static final int MAX_WIND = 18;

    public static final String WIND_SPEED = "wind_spd";
    public static final String TEMPERATURE = "temp";
    public static final String DATA = "data";

    public static String getUrl(String city, String country) {
        return ENDPOINT +"?city=" + city + "&country=" + country + "&key=" + API_KEY;
    }
}
