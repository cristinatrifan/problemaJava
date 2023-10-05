package com.example.problemajava.csv;

import com.example.problemajava.forecast.WeatherAverageForecastDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
class CsvHandlerTest {
    @Autowired
    private CsvHandler csvHandler;

    @Test
    void createForecastCsv() throws IOException {
        //given
        WeatherAverageForecastDto dto = WeatherAverageForecastDto.builder()
                .result(List.of(WeatherAverageForecastDto.CityWeatherDTO.builder()
                        .name("Cluj-Napoca")
                        .temperature("18")
                        .wind("30")
                        .build()))
                .build();

        //when
        csvHandler.createForecastCsv(dto);

        //then
        CSVParser parser = new CSVParser(new FileReader("src/test/java/com/example/problemajava/csv/testForecast.csv"),
                CSVFormat.newFormat(',').withFirstRecordAsHeader());
        List<CSVRecord> records = parser.getRecords();

        assertThat(records.get(0).get("Name")).isEqualTo("Cluj-Napoca");
        assertThat(records.get(0).get(" temperature")).isEqualTo("18");
        assertThat(records.get(0).get(" wind")).isEqualTo("30");
    }
}