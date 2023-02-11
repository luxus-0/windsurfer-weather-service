package com.github.windurferweather.weather;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<WeatherResponse, Long> {
}
