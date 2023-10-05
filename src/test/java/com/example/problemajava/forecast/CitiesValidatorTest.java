package com.example.problemajava.forecast;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CitiesValidatorTest {
    @Autowired
    private CitiesValidator citiesValidator;

    @Test
    void getValidatedCities() {
        //given
        List<String> cities = List.of("Cluj-Napoca","Bucuresti","Craiova","Timisoara",
                "Dej","Constanta","Cluj-Napoca","Baia-Mare","Arad","Bistrita","Oradea");
        Stream<String> expectedStream = Stream.of("Arad", "Baia-Mare", "Bucuresti", "Cluj-Napoca", "Constanta", "Timisoara");

        //when
        Stream<String> actualStream = citiesValidator.getValidatedCities(cities);

        //then
        assertThat(actualStream.collect(Collectors.toList())).isEqualTo(expectedStream.collect(Collectors.toList()));
    }
}