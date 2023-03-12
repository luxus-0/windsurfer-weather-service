package com.github.windsurferweather.mapper;

import com.github.windsurferweather.model.WeatherReport;
import com.github.windsurferweather.model.dto.WeatherReportDto;

public class WeatherReportMapper {
    public static WeatherReportDto mapToWeatherReportDto(WeatherReport weatherReport){
        return new WeatherReportDto(weatherReport.getCity(), weatherReport.getCountry() ,weatherReport.getLat(), weatherReport.getLon(), weatherReport.getStart(), weatherReport.getEnd(), weatherReport.getTemperature(), weatherReport.getWindSpeed());
    }
}
