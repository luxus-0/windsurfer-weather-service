package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;

@Log4j2
class BestLocationWeatherForWindsurferMessage {
    static void showBestLocationForWindsurfer(WeatherConditionDto bestLocationForWindsurfing) {
        log.info("\nBest location for windsurfing in Date: " + bestLocationForWindsurfing.date() + "\n" +
                "City: " +bestLocationForWindsurfing.city() + "\n" +
                "Country: " +bestLocationForWindsurfing.country() + "\n" +
                "WindSpeed: " +bestLocationForWindsurfing.windSpeed() + "\n" +
                "Temperature: " + bestLocationForWindsurfing.temp() + "\n");
    }
}
