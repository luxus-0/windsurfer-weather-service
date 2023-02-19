package com.github.windurferweather.weather;

class WeatherConditionDto {
    private double windSpeed;
    private double temperature;

    public WeatherConditionDto(double windSpeed, double temperature) {
        this.windSpeed = windSpeed;
        this.temperature = temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
