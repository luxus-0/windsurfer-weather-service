package com.github.windurferweather.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static com.github.windurferweather.weather.AirQualityConstant.getAirQualityUrl;
import static com.github.windurferweather.weather.AirQualityMapper.getData;
import static com.github.windurferweather.weather.WeatherConstant.DATA;
import static java.util.Comparator.comparingDouble;

@Service
@Log4j2
class AirQualityImpl {
    private final AirQualityRepository airQualityRepository;

    AirQualityImpl(AirQualityRepository airQualityRepository) {
        this.airQualityRepository = airQualityRepository;
    }

    AirQuality findAirQualityByLocation(String city) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String air_quality_url = getAirQualityUrl(city);
        URL url = new URL(air_quality_url);
        JsonNode rootNode = objectMapper.readTree(url);
       JsonNode dataNode =  rootNode.path(DATA).get(0);

       JsonNode pm25_node = dataNode.get("pm25");
        JsonNode pm10_node = dataNode.get("pm10");
        JsonNode aqi_node = dataNode.get("aqi");

        double pm25 = pm25_node.asDouble();
        double pm10 = pm10_node.asDouble();
        int aqi = aqi_node.asInt();

        AirQualityDto airQualityDto = new AirQualityDto(city, aqi, pm25, pm10);
        return airQualityRepository.save(createAirQuality(airQualityDto));
    }

    private AirQuality createAirQuality(AirQualityDto airQualityDto) {
        Data data = getData(airQualityDto);
        AirQuality airQuality = AirQuality.builder()
                .data(List.of(data))
                .city(airQualityDto.getCity())
                .pm10(airQualityDto.getPm10())
                .pm25(airQualityDto.getPm25())
                .build();

        AirQuality airQualitySaved = airQualityRepository.save(airQuality);
        return new AirQuality(airQualitySaved.getCity(), airQualitySaved.getData(), airQualitySaved.getPm25(), airQualitySaved.getPm10());
    }

    private static AirQuality retrieveAirQuality(AirQualityDto airQualityDto) {
        return Stream.of(airQualityDto)
                .filter(Objects::nonNull)
                .map(AirQualityMapper::mapToAirQuality)
                .findAny()
                .orElse(null);
    }

    private Double receiveTheBiggestPm25() {
        return airQualityRepository.findAll()
                .stream()
                .map(AirQuality::getPm25)
                .sorted()
                .max(Comparator.comparingDouble(Double::doubleValue))
                .stream()
                .findFirst()
                .orElse(0d);
    }

    private Double receiveTheBiggestPm10() {
        Optional<Double> theBiggestPm10 = airQualityRepository.findAll()
                .stream()
                .map(AirQuality::getPm10)
                .sorted()
                .max(comparingDouble(Double::doubleValue))
                .stream()
                .findFirst();

        theBiggestPm10.ifPresent(printTheBiggestPollutionPm10 -> log.info("Biggest Pollution by PM10 is: " + theBiggestPm10.get()));

        return theBiggestPm10.orElse(0d);
    }

    private AirQuality createPM10(AirQuality p) {
        return AirQuality.builder()
                .pm10(p.getPm10())
                .build();
    }
}
