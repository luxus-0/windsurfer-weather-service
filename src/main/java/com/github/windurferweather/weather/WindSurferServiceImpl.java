package com.github.windurferweather.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.windurferweather.weather.io.WindSurferWeatherWriter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.*;

@Log4j2
@Service
class WindSurferServiceImpl implements WindSurferLocationService {

    private final WindSurferWeatherWriter windSurferWeatherWriter;

    WindSurferServiceImpl(WindSurferWeatherWriter windSurferWeatherWriter) {
        this.windSurferWeatherWriter = windSurferWeatherWriter;
    }

    @Override
    public WindSurferWeatherDto readWindSurfingLocationByDate(String datetime) throws Exception {
        String windsurferWeather = windSurferWeatherWriter.writeFromWindSurferWeatherURLToFileCSV(datetime);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(windsurferWeather, WindSurferWeatherDto.class);
    }

    boolean checkBestPlaceForWindSurfer(double wind, double temperature) {
        return wind >= MIN_WIND && wind <= MAX_WIND &&
                temperature >= MIN_TEMP && temperature <= MAX_TEMP;
    }

    double findBestLocalizationForSurfer(double windSpeed, double temp) {
        return (windSpeed * 3) + temp;
    }
}