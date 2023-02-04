package com.github.windurferweather.weather;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
class WeatherResponse {
    private double avgTemp;
    private double windSpeed;
    private LocalDate date;

    public WeatherResponse() {
    }

    public WeatherResponse(LocalDate date, double avgTemp, double windSpeed) {
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

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
