package com.github.windurferweather.weather;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class DateValid {
    static boolean isValid (String actualDate){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(actualDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
