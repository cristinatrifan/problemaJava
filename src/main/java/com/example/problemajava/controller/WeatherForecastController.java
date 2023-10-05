package com.example.problemajava.controller;

import com.example.problemajava.forecast.WeatherAverageForecastDto;
import com.example.problemajava.forecast.WeatherAverageForecastService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WeatherForecastController {
    private static final String PATH = "/api/weather";
    private final WeatherAverageForecastService service;

    /**
     * Fetches average forecast temperature and wind data for one or more of the following cities:
     * Cluj-Napoca, Bucuresti, Timisoara, Constanta, Baia-Mare, Arad.
     */
    @GetMapping(value = PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public WeatherAverageForecastDto getForecast(@RequestParam List<String> city) {
        log.info("Fetching average forecast data.");

        return service.getAverageForecastForCities(city);
    }

}
