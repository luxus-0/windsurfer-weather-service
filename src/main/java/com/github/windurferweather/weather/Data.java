package com.github.windurferweather.weather;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor
class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int aqi;
    public Data() {
    }

    public Data(int aqi) {
        this.aqi = aqi;
    }
}
