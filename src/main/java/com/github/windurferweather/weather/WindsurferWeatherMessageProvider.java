package com.github.windurferweather.weather;

public class WindsurferWeatherMessageProvider {

    public static String getWeatherUrl(String city, String country){
        return WEATHER_URL + "daily?" + "city=" + city +"&country=" +country+ "&key=" + API_KEY;
    }
    public static final String WEATHER_URL = "https://api.weatherbit.io/v2.0/forecast/";

    public static final String API_KEY = "273c07c4d603455cabd9f94cb58b526c";

    public static final int MIN_TEMP = 3;
    public static final int MAX_TEMP = 35;
    public static final int MIN_WIND = 5;
    public static final int MAX_WIND = 18;
}