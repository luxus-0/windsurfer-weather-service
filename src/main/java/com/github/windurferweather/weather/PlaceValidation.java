package com.github.windurferweather.weather;

import org.springframework.stereotype.Service;

@Service
public class PlaceValidation {
    public boolean isValid(String city, String country) {
        if (city == null || country == null) {
            throw new IllegalArgumentException("City or country is null");
        }
        return true;
    }
}
