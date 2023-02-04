package com.github.windurferweather.weather;

import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
class WeatherResponse {
    private double avgTemp;
    private BigDecimal windSpeed;
    private LocalDate date;

    public WeatherResponse() {
    }

    public WeatherResponse(LocalDate date, double avgTemp, BigDecimal windSpeed) {
        this.date = date;
        this.avgTemp = avgTemp;
        this.windSpeed = windSpeed;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public BigDecimal getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(BigDecimal windSpeed) {
        this.windSpeed = windSpeed;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
