package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.time.LocalDate.now;

@Service
@Log4j2
class DateValidation {

    private static final long NUMBER_DAYS = 16L;

    boolean isValid(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(true);
        try {
            df.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    boolean isInRange(String date) {
        return LocalDate.parse(date).isBefore(now().plus(NUMBER_DAYS, ChronoUnit.DAYS));
    }
}
