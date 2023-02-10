package com.github.windurferweather.weather;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

import java.time.LocalDate;

@Entity
@Builder
class WeatherResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double temp;
    private double windSpeed;
    private LocalDate date;

    public WeatherResponse() {
    }

    public WeatherResponse(double temp, double windSpeed, LocalDate date) {
        this.temp = temp;
        this.windSpeed = windSpeed;
        this.date = date;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
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
