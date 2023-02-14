package com.github.windurferweather.weather.io;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.*;

@Log4j2
@Service
public class WindSurferWeatherReader {

    private static final StringBuilder windsurferWeatherBuilder = new StringBuilder();

    public String readWindSurferWeatherByDate(String datetime) throws Exception {
        URL weatherUrl = new URL(getUrl(datetime));

        BufferedReader windsurferWeatherReader = new BufferedReader(new InputStreamReader(weatherUrl.openStream()));
        String line;

        while ((line = windsurferWeatherReader.readLine()) != null){
           windsurferWeatherBuilder.append(line);
        }
        return windsurferWeatherBuilder.toString();
    }
}
