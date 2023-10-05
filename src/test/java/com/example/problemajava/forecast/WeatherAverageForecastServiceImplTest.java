package com.example.problemajava.forecast;

import com.example.problemajava.api.WeatherApi;
import com.example.problemajava.api.WeatherApiDto;
import com.example.problemajava.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherAverageForecastServiceImplTest {
    @InjectMocks
    private WeatherAverageForecastServiceImpl service;
    @Mock
    private WeatherApi weatherApi;
    @Mock
    private CitiesValidator citiesValidator;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    void shouldGetAverageForecastForCities() {
        //given
        List<String> cities = List.of("Cluj-Napoca");
        Stream<String> validatedStream = Stream.of("Cluj-Napoca");
        WeatherApiDto apiDto = new WeatherApiDto("temperature", "wind", "description",
                List.of(new WeatherApiDto.ForecastDTO("1", "15", "20")));
        WeatherAverageForecastDto expectedDto = WeatherAverageForecastDto.builder()
                .result(List.of(WeatherAverageForecastDto.CityWeatherDTO.builder()
                                .name("Cluj-Napoca")
                                .temperature("15")
                                .wind("20")
                        .build()))
                .build();

        when(citiesValidator.getValidatedCities(cities)).thenReturn(validatedStream);
        when(weatherApi.getWeatherDetails("Cluj-Napoca")).thenReturn(apiDto);
        doNothing().when(applicationEventPublisher).publishEvent(any(NewWeatherForecastEvent.class));

        //when
        WeatherAverageForecastDto actualDto = service.getAverageForecastForCities(cities);

        //then
        assertThat(actualDto).isNotNull();
        assertThat(actualDto.getResult()).hasSize(1);
        assertThat(actualDto.getResult().get(0).getName()).isEqualTo(expectedDto.getResult().get(0).getName());
        assertThat(actualDto.getResult().get(0).getTemperature()).isEqualTo(expectedDto.getResult().get(0).getTemperature());
        assertThat(actualDto.getResult().get(0).getWind()).isEqualTo(expectedDto.getResult().get(0).getWind());
    }

    @Test
    void shouldNotGetAverageForecastForCities() {
        //given
        List<String> cities = List.of("Turda");
        Stream<String> validatedStream = Stream.empty();

        when(citiesValidator.getValidatedCities(cities)).thenReturn(validatedStream);

        //when & then
        assertThrows(BusinessException.class, () -> service.getAverageForecastForCities(cities));
    }
}