package com.example.problemajava.forecast;

import java.util.List;

public interface WeatherAverageForecastService {
    WeatherAverageForecastDto getAverageForecastForCities(List<String> requestedCitiesList);
}
