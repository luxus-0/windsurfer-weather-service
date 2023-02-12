package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
@Log4j2
class DateValidation {
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
}
