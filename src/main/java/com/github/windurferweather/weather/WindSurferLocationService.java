package com.github.windurferweather.weather;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface WindSurferLocationService {
    WindSurferWeatherDto readWindSurfingLocationByDate(String date) throws Exception;
}
