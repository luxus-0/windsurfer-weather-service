package com.github.windsurferweather.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    public static String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
