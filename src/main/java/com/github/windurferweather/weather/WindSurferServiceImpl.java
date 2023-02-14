package com.github.windurferweather.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.net.URL;

import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.*;

@Log4j2
@Service
class WindSurferServiceImpl implements WindSurferLocationService {

    private final WindSurferWeatherClient windSurferWeatherClient;
    private final DateValidatorImpl dateValidator;

    WindSurferServiceImpl(WindSurferWeatherClient windSurferWeatherClient, DateValidatorImpl dateValidator) {
        this.windSurferWeatherClient = windSurferWeatherClient;
        this.dateValidator = dateValidator;
    }

    @Override
    public WindSurferWeatherDto readWindSurfingLocationByDate(String datetime) {

        WindSurferWeatherDto windSurferWeatherDto = windSurferWeatherClient.readWindSurfingByDate(datetime);

        String city = windSurferWeatherDto.localizationDto().city();
        String country = windSurferWeatherDto.localizationDto().country();
        double temp = windSurferWeatherDto.temperature();
        double windSpeed = windSurferWeatherDto.windSpeed();


        return new WindSurferWeatherDto(new LocalizationDto(city, country), windSpeed, temp);

    }

    boolean checkBestPlaceForWindSurfer(double wind, double temperature) {
        return wind >= MIN_WIND && wind <= MAX_WIND &&
                temperature >= MIN_TEMP && temperature <= MAX_TEMP;
    }

    double findBestLocalizationForSurfer(double windSpeed, double temp) {
        return (windSpeed * 3) + temp;
    }
}