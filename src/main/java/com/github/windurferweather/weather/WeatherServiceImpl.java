package com.github.windurferweather.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.windurferweather.weather.dto.LocationDto;
import com.github.windurferweather.weather.dto.WeatherResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.github.windurferweather.weather.WeatherConstant.*;

@Log4j2
@Service
class WeatherServiceImpl implements WeatherService {
    private final WeatherClient weatherClient;

    WeatherServiceImpl(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    @Override
    public WeatherResponseDto readTheBestLocationForWindsurfing(String day) {
        List<LocationDto> locations = createLocation();
        LocalDate date = LocalDate.parse(day, DateTimeFormatter.ISO_DATE);

        return locations.stream()
                .map(location -> getWeather(date, location))
                .filter(this::isSuitableForWindsurfingWeather)
                .max(Comparator.comparingDouble(this::calculateForWindsurfingLocation))
                .orElse(new WeatherResponseDto(new LocationDto("","",0,0),0, 0,""));
    }

    private WeatherResponseDto getWeather(LocalDate date, LocationDto location) {
        LocationDto locationDto = getLocationDto(location);
        try {
            return getWeatherResponseDto(date, locationDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private WeatherResponseDto getWeatherResponseDto(LocalDate date, LocationDto location) throws IOException {
        ResponseEntity<String> response = weatherClient.getForecastWeather(location);
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode data = root.get("data");
                if (data != null) {
                    JsonNode jsonNode = data.get(0);
                    double temperature = jsonNode.get("temp").asDouble();
                    double windSpeed = jsonNode.get("wind_spd").asDouble();

                    return new WeatherResponseDto(location, windSpeed, temperature, date.toString());
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        throw new BestWindSurfingWeatherForLocationNotFound();
    }

    private LocationDto getLocationDto(LocationDto weather) {
        return new LocationDto(weather.city(), weather.country(), weather.lat(), weather.lon());
    }

    private List<LocationDto> createLocation() {
        List<LocationDto> locations = new ArrayList<>();
        locations.add(new LocationDto("Jastarnia", "Poland", 54.6971, 18.6804));
        locations.add(new LocationDto("Bridgetown", "Barbados", 13.0969, -59.6145));
        locations.add(new LocationDto("Fortaleza", "Brazil", -3.7184, -38.5434));
        locations.add(new LocationDto("Pissouri", "Cyprus", 34.6704, 32.7084));
        locations.add(new LocationDto("Le Morne", "Mauritius", -20.4019, 57.3142));
        return locations;
    }

    private double calculateForWindsurfingLocation(WeatherResponseDto weather) {
        return weather.getWind_spd() * 3 + weather.getTemp();
    }

    private boolean isSuitableForWindsurfingWeather(WeatherResponseDto weatherResponse) {
        return weatherResponse.getWind_spd() >= MIN_WIND && weatherResponse.getWind_spd() <= MAX_WIND
                && weatherResponse.getTemp() >= MIN_TEMP && weatherResponse.getTemp() <= MAX_TEMP;
    }
}