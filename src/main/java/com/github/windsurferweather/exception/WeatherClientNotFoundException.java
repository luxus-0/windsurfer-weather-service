package com.github.windsurferweather.exception;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class WeatherClientNotFoundException extends RuntimeException {
    public WeatherClientNotFoundException(String error) {
        log.info(error);
    }
}
