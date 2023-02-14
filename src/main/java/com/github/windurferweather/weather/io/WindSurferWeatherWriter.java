package com.github.windurferweather.weather.io;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import static com.github.windurferweather.weather.WindsurferWeatherMessageProvider.getUrl;
import static com.github.windurferweather.weather.io.FilePath.FILE_PATH;

@Service
@Log4j2
public class WindSurferWeatherWriter {


    private final WindSurferWeatherReader windSurferWeatherReader;
    private static final StringBuilder windsurferWeatherBuilder = new StringBuilder();

    public WindSurferWeatherWriter(WindSurferWeatherReader windSurferWeatherReader) {
        this.windSurferWeatherReader = windSurferWeatherReader;
    }


    public String writeFromWindSurferWeatherURLToFileCSV(String datetime) throws Exception {
        String weatherUrl = getUrl(datetime);
        URL url = new URL(weatherUrl);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);

        OutputStream outputStream = new FileOutputStream(FILE_PATH);
        OutputStreamWriter out = new OutputStreamWriter(outputStream);

        String readWeather = windSurferWeatherReader.readWindSurferWeatherByDate(datetime);
        out.write(readWeather);
        StringBuilder windsurferWeather = windsurferWeatherBuilder.append(readWeather);
        log.info(windsurferWeather);
        return windsurferWeather.toString();
    }
}
