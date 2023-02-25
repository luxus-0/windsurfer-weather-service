package com.github.windurferweather.weather;

import java.util.Map;

class LocationCreator {
    static Map<String, String> createLocations() {
        return Map.of(
                "Jastarnia", "PL",
                "Bridgetown", "BB",
                "Fortaleza", "BR",
                "Pissouri", "CY",
                "Le Mont", "CH"
        );
    }
}
