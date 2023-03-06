package com.github.windsurferweather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Data {
    @JsonProperty("max_temp")
    protected double maxTemperature;
    @JsonProperty("min_temp")
    protected double minTemperature;
    @JsonProperty("wind_spd")
    protected double windSpeed;
    @JsonProperty("valid_date")
    protected String date;

    public Data() {
    }

    public Data(double maxTemperature, double minTemperature, double windSpeed, String date) {
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.windSpeed = windSpeed;
        this.date = date;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return Double.compare(data.maxTemperature, maxTemperature) == 0 &&
                Double.compare(data.minTemperature, minTemperature) == 0 &&
                Double.compare(data.windSpeed, windSpeed) == 0 &&
                Objects.equals(date, data.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxTemperature, minTemperature, windSpeed, date);
    }
}
