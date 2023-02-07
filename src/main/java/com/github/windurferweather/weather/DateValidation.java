package com.github.windurferweather.weather;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Log4j2
class DateValidation {
    boolean isValid (String actualDate){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(actualDate);
        } catch (ParseException e) {
            log.info("Incorrect date");
            return false;
        }
        log.info("Correct date");
        return true;
    }
}
