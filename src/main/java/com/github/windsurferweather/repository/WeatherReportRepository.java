package com.github.windsurferweather.repository;

import com.github.windsurferweather.model.WeatherReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherReportRepository extends JpaRepository<WeatherReport, Long> {
    List<WeatherReport> findAllByCityAndCountry(String city, String country);
    List<WeatherReport> findAllByCityAndCountryAndStartAndEndBetween(String city, String country, LocalDateTime startTime, LocalDateTime endTime);
}
