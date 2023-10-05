package com.example.problemajava.forecast;

import lombok.Getter;

import java.util.EventObject;

@Getter
public class NewWeatherForecastEvent extends EventObject {
    private final WeatherAverageForecastDto forecastDto;
    public NewWeatherForecastEvent( WeatherAverageForecastDto forecastDto) {
        super(WeatherAverageForecastDto.class);
        this.forecastDto = forecastDto;
    }
}
