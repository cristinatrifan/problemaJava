package com.example.problemajava.csv;

import java.io.FileWriter;
import java.util.stream.Collectors;

import com.example.problemajava.forecast.WeatherAverageForecastDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class CsvHandler {
    @Value("${csv.path}")
    private String path;

    @SneakyThrows
    public void createForecastCsv(WeatherAverageForecastDto forecastDto) {
        String csvHeader = "Name, temperature, wind";

        log.info("Creating new version of forecast.csv");

        try (CSVPrinter printer = new CSVPrinter(new FileWriter(path), CSVFormat.DEFAULT
                .withHeader(csvHeader)
                .withDelimiter(',')
                .withQuote(null)
        )) {
            printer.printRecords(
                    forecastDto.getResult().stream()
                            .map(entry -> entry.getName() + "," + entry.getTemperature() + "," + entry.getWind())
                            .collect(Collectors.toList()));
        }
    }

}
