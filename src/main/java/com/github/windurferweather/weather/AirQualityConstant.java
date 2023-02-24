package com.github.windurferweather.weather;

import static com.github.windurferweather.weather.WeatherEndpoint.ENDPOINT;

class AirQualityConstant {
    public static final String API_KEY = "273c07c4d603455cabd9f94cb58b526c";
    public static final String AIR_QUALITY_URL = "http://api.weatherbit.io/v2.0/current/airquality";
    public static final String DATA = "data";

    public static String getAirQualityUrl(String city) {
        return AIR_QUALITY_URL +"?city=" + city + "&key=" + API_KEY;
    }
}
