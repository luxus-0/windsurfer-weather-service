package com.github.windurferweather.weather;

import com.github.windurferweather.weather.io.WindSurferWeatherReader;
import com.github.windurferweather.weather.io.WindSurferWeatherWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WindSurferWeatherApi {

    private final WindSurferServiceImpl windSurferServiceImpl;
    private final WindSurferWeatherWriter windSurferWeatherWriter;

    WindSurferWeatherApi(WindSurferServiceImpl windSurferServiceImpl, WindSurferWeatherWriter windSurferWeatherWriter) {
        this.windSurferServiceImpl = windSurferServiceImpl;
        this.windSurferWeatherWriter = windSurferWeatherWriter;
    }

    @GetMapping("/weather/{date}")
    WindSurferWeatherDto readWindSurferWeatherLocalizationByDate(@PathVariable String date){
        return windSurferServiceImpl.readWindSurfingLocationByDate(date);
    }

    @GetMapping("/weather/write_to_csv_file/{city}/{country}")
    String writeWeatherFromURLToCSV(@PathVariable String city, @PathVariable String country) throws Exception {
        return windSurferWeatherWriter.writeFromWindSurferWeatherLocalizationURLToFileCSV(city, country);
    }
}
