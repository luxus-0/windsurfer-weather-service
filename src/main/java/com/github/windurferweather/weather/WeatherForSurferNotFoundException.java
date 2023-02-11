package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;

@Log4j2
class WeatherForSurferNotFoundException extends RuntimeException{
    WeatherForSurferNotFoundException(){
        log.info("Weather for surfing not found");
    }
}
