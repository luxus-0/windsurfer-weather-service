package com.github.windsurferweather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class Weather {

    @JsonProperty("city_name")
    private String cityName;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("lat")
    private String lat;
    @JsonProperty("lon")
    private String lon;
    @JsonProperty("data")
    private List<Data> data;

    public Weather() {
    }

    public Weather(String cityName, String countryCode, String lat, String lon, List<Data> data) {
        this.cityName = cityName;
        this.countryCode = countryCode;
        this.lat = lat;
        this.lon = lon;
        this.data = data;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Objects.equals(cityName, weather.cityName) &&
                Objects.equals(countryCode, weather.countryCode) &&
                Objects.equals(lat, weather.lat) &&
                Objects.equals(lon, weather.lon) &&
                Objects.equals(data, weather.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityName, countryCode, lat, lon, data);
    }
}