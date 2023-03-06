package com.github.windsurferweather.model;

import java.util.Objects;

public class BestWeather extends Data {

    private String city;
    private String date;

    public BestWeather(String cityName, Data data) {
        this.city = cityName;
        this.date = data.getDate();
        this.windSpeed = data.getWindSpeed();
        this.maxTemperature = data.getMaxTemperature();
        this.minTemperature = data.getMinTemperature();
    }

    public BestWeather(Data data) {
        this.city = "Brak możliwości do surfowania";
        this.date = data.getDate();
        this.windSpeed = data.getWindSpeed();
        this.maxTemperature = data.getMaxTemperature();
        this.minTemperature = data.getMinTemperature();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BestWeather that = (BestWeather) o;
        return Objects.equals(city, that.city) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), city, date);
    }
}
