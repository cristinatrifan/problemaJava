package com.example.problemajava.forecast;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class WeatherAverageForecastDto {
    private List<CityWeatherDTO> result;

    @Builder
    @Setter
    @Getter
    public static class CityWeatherDTO {
        private String name;
        private String temperature;
        private String wind;
    }
}
