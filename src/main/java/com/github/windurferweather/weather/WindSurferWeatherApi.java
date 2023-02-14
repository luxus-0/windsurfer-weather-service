package com.github.windurferweather.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.windurferweather.weather.io.WindSurferWeatherWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class WindSurferWeatherApi {

    private final WindSurferServiceImpl windSurferServiceImpl;
    private final WindSurferWeatherWriter windSurferWeatherWriter;

    WindSurferWeatherApi(WindSurferServiceImpl windSurferServiceImpl, WindSurferWeatherWriter windSurferWeatherWriter) {
        this.windSurferServiceImpl = windSurferServiceImpl;
        this.windSurferWeatherWriter = windSurferWeatherWriter;
    }

    @GetMapping("/weather/{date}")
    WindSurferWeatherDto readWindSurferWeatherLocalizationByDate(@PathVariable String date) throws Exception {
        return windSurferServiceImpl.readWindSurfingLocationByDate(date);
    }

    @GetMapping("/weather/write_to_csv_file/{datetime}")
    String writeWeatherFromURLToCSV(@PathVariable String datetime) throws Exception {
        return windSurferWeatherWriter.writeFromWindSurferWeatherURLToFileCSV(datetime);
    }
}
