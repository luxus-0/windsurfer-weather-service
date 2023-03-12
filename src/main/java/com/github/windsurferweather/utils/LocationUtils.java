package com.github.windsurferweather.utils;

/**
*  // oblicza odległość między dwoma miejscami na podstawie ich współrzędnych geograficznych
* Do obliczenia odległości wykorzystuje wzór Haversine'a, który uwzględnia krzywiznę Ziemi.
* */

public class LocationUtils {
    public static double readDistanceBetween(double lat, double lon) {
        final double earthRadius = 6371;
        double lat1 = Math.toRadians(lat);
        double lon1 = Math.toRadians(lon);
        double lat2 = Math.toRadians(lat);
        double lon2 = Math.toRadians(lon);
        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }
}
