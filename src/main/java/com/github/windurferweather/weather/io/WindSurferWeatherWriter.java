package com.github.windurferweather.weather.io;

import com.github.windurferweather.weather.PlaceValidation;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.*;
import static com.github.windurferweather.weather.io.FilePath.FILE_PATH;
import static com.github.windurferweather.weather.io.WindSurferWeatherReader.readWindSurferWeatherByLocalization;

@Service
@Log4j2
public class WindSurferWeatherWriter {


    private final PlaceValidation placeValidation;
    private static final StringBuilder windsurferWeatherBuilder = new StringBuilder();

    public WindSurferWeatherWriter(PlaceValidation placeValidation) {
        this.placeValidation = placeValidation;
    }

    public String writeFromWindSurferWeatherLocalizationURLToFileCSV(String city, String country) throws Exception {
        placeValidation.isValid(city, country);
        String weatherUrl = getWeatherUrl(city, country);
        URL url = new URL(weatherUrl);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        OutputStream outputStream = new FileOutputStream(FILE_PATH);
        OutputStreamWriter out = new OutputStreamWriter(outputStream);
        String read = readWindSurferWeatherByLocalization(city, country);
        out.write(read);
        StringBuilder windsurferWeatherByCityAndCountry = windsurferWeatherBuilder.append(read);
        log.info(windsurferWeatherByCityAndCountry);
        return windsurferWeatherByCityAndCountry.toString();
    }
}
