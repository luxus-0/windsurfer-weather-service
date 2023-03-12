package com.github.windsurferweather.service;

import com.github.windsurferweather.model.WeatherReport;
import com.github.windsurferweather.model.dto.WeatherReportDto;
import com.github.windsurferweather.repository.WeatherReportRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.github.windsurferweather.mapper.WeatherReportMapper.mapToWeatherReportDto;
import static com.github.windsurferweather.utils.LocationUtils.readDistanceBetween;

@Service
public class WeatherReportService {

    private final WeatherReportRepository weatherReportRepository;

    public WeatherReportService(WeatherReportRepository weatherReportRepository) {
        this.weatherReportRepository = weatherReportRepository;
    }

    public WeatherReportDto formWeatherReport(WeatherReport weatherReport){
        return mapToWeatherReportDto(weatherReportRepository.save(weatherReport));
    }

    public List<WeatherReport> readWeatherReport(){
        return weatherReportRepository.findAll();
    }

    public Double readAverageTemperatureForLocationInTimeFrame(String city, String country, LocalDateTime start, LocalDateTime end) {
        List<WeatherReport> weatherReportsByLocation = weatherReportRepository.findAllByCityAndCountryAndStartAndEnd(city, country, start, end);
        double averageTemp = 0;
        if(weatherReportsByLocation != null){
            averageTemp =  weatherReportsByLocation.stream()
                    .mapToDouble(WeatherReport::getTemperature)
                    .summaryStatistics()
                    .getAverage();
        }
        return Optional.of(averageTemp).orElse(0d);
    }

    public Double readMaxWindSpeedForLocationInTimeFrame(String city, String country, LocalTime start, LocalTime end){
        List<WeatherReport> weatherReports = weatherReportRepository.findAllByCityAndCountry(city, country);

        if(weatherReports != null) {

            Duration duration = Duration.between(start, end);
            long hours = duration.toHours();
            long minutes = duration.toMinutes();

            return getMaxWindSpeedInPeriodTime(weatherReports, hours, minutes);
        }
        return 0d;
    }

    private double getMaxWindSpeedInPeriodTime(List<WeatherReport> weatherReports, long hours, long minutes) {
        return weatherReports.stream()
                .filter(timeValid -> hours > 0 && minutes >= 0)
                .mapToDouble(WeatherReport::getWindSpeed)
                .max()
                .orElse(-1111);
    }

    public List<WeatherReportDto> readWeatherReportForLocationsNearby(double lat, double lon, double distance) {
        return weatherReportRepository.findAll()
                .stream()
                .filter(location -> readDistanceBetween(lat, lon) <= distance)
                .map(weatherReport -> getWeatherReportDto(lat, lon, weatherReport))
                .toList();
    }

    private WeatherReportDto getWeatherReportDto(double lat, double lon, WeatherReport weatherReport) {
        return WeatherReportDto.builder()
                .city(weatherReport.getCity())
                .country(weatherReport.getCountry())
                .lat(lat)
                .lon(lon)
                .temperature(weatherReport.getTemperature())
                .windSpeed(weatherReport.getWindSpeed())
                .build();
    }
}
