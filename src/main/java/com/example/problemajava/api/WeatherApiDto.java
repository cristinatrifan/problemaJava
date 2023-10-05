package com.example.problemajava.api;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherApiDto {
    private String temperature;
    private String wind;
    private String description;
    private List<ForecastDTO> forecast;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ForecastDTO {
        private String day;
        private String temperature;
        private String wind;
    }
}
