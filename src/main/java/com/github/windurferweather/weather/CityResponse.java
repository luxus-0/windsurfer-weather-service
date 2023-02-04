package com.github.windurferweather.weather;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
class CityResponse {
    private String city;
    private String country;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public CityResponse() {
    }

    public CityResponse(String city, String country, BigDecimal latitude, BigDecimal longitude) {
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
