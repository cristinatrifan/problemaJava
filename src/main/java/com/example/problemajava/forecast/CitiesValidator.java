package com.example.problemajava.forecast;

import com.example.problemajava.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
@Slf4j
class CitiesValidator {
    public Stream<String> getValidatedCities(List<String> requestedCitiesList) {
        log.debug("Validating requested cities.");

        if(requestedCitiesList.isEmpty()) {
            throw new BusinessException("Please select at least one city for forecast.");
        }

        return requestedCitiesList.stream()
                .distinct()
                //return only the allowed cities
                .filter(city -> Arrays.stream(ForecastedCities.values())
                        .anyMatch(cityEnum -> cityEnum.getCityString().equals(city)))
                //sort alphabetically
                .sorted();
    }
}
