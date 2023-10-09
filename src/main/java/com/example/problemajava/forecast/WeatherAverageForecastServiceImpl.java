package com.example.problemajava.forecast;

import com.example.problemajava.exceptions.BusinessException;
import com.example.problemajava.api.WeatherApi;
import com.example.problemajava.api.WeatherApiDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class WeatherAverageForecastServiceImpl implements WeatherAverageForecastService {
    private final WeatherApi weatherApi;
    private final CitiesValidator citiesValidator;
    private final ApplicationEventPublisher applicationEventPublisher;

    public WeatherAverageForecastDto getAverageForecastForCities(List<String> requestedCitiesList) {
        List<WeatherAverageForecastDto.CityWeatherDTO> cityWeatherDTOS = new ArrayList<>();

        citiesValidator.getValidatedCities(requestedCitiesList)
                .forEach(city -> {
                    try {
                        WeatherApiDto apiDto = weatherApi.getWeatherDetails(city);
                        cityWeatherDTOS.add(getAverageForecastByCity(city, apiDto));
                    } catch (RuntimeException e) {
                        log.error(e.getLocalizedMessage());
                        cityWeatherDTOS.add(getInitialCityWeatherDTO(city));
                    }
                });

        if (cityWeatherDTOS.isEmpty()) {
            throw new BusinessException("There is no forecast for your city selection.");
        }

        WeatherAverageForecastDto weatherAverageForecastDto = WeatherAverageForecastDto.builder()
                .result(cityWeatherDTOS)
                .build();

        //Trigger CSV creation
        applicationEventPublisher.publishEvent(new NewWeatherForecastEvent(weatherAverageForecastDto));

        return weatherAverageForecastDto;
    }

    private WeatherAverageForecastDto.CityWeatherDTO getAverageForecastByCity(String city, WeatherApiDto apiDto) {
        List<Integer> tempList = new ArrayList<>();
        List<Integer> windList = new ArrayList<>();

        WeatherAverageForecastDto.CityWeatherDTO cityWeatherDTO = getInitialCityWeatherDTO(city);

        apiDto.getForecast().forEach(forecast -> {
            tempList.add(getElementAsInteger(forecast.getTemperature()));
            windList.add(getElementAsInteger(forecast.getWind()));
        });

        tempList.stream().mapToInt(Integer::intValue).average()
                .ifPresentOrElse(avrTemp -> cityWeatherDTO.setTemperature(getAverageAsString(avrTemp)),
                        () -> log.error("There are no temperature forecast values for city " + city));

        windList.stream().mapToInt(Integer::intValue).average()
                .ifPresentOrElse(avrWind -> cityWeatherDTO.setWind(getAverageAsString(avrWind)),
                        () -> log.error("There are no wind forecast values for city " + city));

        return cityWeatherDTO;
    }

    /**
     * Temperature and wind values should not be presented with decimal values
     * They are rounded down
     */
    private static String getAverageAsString(double avrTemp) {
        return String.valueOf(BigDecimal.valueOf(avrTemp).intValue());
    }

    private Integer getElementAsInteger(String forecastElement) {
        try {
            return Integer.valueOf(forecastElement);
        } catch (NumberFormatException e) {
            throw new BusinessException("The forecast is corrupt and the average cannot be calculated.");
        }
    }

    private WeatherAverageForecastDto.CityWeatherDTO getInitialCityWeatherDTO(String city) {
        return WeatherAverageForecastDto.CityWeatherDTO.builder()
                .name(city)
                .temperature("")
                .wind("")
                .build();
    }

}
