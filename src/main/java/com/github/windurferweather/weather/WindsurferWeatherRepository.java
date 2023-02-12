package com.github.windurferweather.weather;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WindsurferWeatherRepository extends JpaRepository<WindSurferWeather, Long> {
}
