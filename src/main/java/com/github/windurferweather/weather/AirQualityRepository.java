package com.github.windurferweather.weather;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AirQualityRepository extends JpaRepository<AirQuality, Long> {
}