package com.github.windurferweather.weather.io;

import com.github.windurferweather.weather.PlaceValidation;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.*;

@Log4j2
@Service
public class WindSurferWeatherReader {

    private static final PlaceValidation placeValidation = new PlaceValidation();
    private static final StringBuilder windsurferWeatherBuilder = new StringBuilder();

    static String readWindSurferWeatherByLocalization(String city, String country) throws Exception {
        placeValidation.isValid(city, country);
        String weatherUrl = getWeatherUrl(city, country);
        URL url = new URL(weatherUrl);

        BufferedReader windsurferWeatherReader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;

        while ((line = windsurferWeatherReader.readLine()) != null){
           windsurferWeatherBuilder.append(line);
        }
        return windsurferWeatherBuilder.toString();
    }
}
