package com.example.problemajava.csv;

import com.example.problemajava.forecast.NewWeatherForecastEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewWeatherForecastEventListener {
    private final CsvHandler service;

    @EventListener
    public void handle(NewWeatherForecastEvent event) {
        log.debug("Event {} triggered.", event.getClass().getName());

        service.createForecastCsv(event.getForecastDto());
    }
}
