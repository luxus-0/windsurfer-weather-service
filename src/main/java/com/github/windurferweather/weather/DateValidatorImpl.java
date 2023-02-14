package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
@Log4j2
public class DateValidatorImpl implements DateValidator {

    @Override
    public boolean isValid(String date) {
        DateFormat sdf = new SimpleDateFormat(date);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
