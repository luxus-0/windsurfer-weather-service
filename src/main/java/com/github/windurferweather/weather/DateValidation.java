package com.github.windurferweather.weather;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static java.util.Objects.requireNonNull;

class DateValidation {
    static ResponseEntity<?> valid(String date) {
        requireNonNull(date);
        LocalDate dateWeather;
        try {
            dateWeather = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(dateWeather);
    }
}
