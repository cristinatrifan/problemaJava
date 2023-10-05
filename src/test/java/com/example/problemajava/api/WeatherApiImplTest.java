package com.example.problemajava.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherApiImplTest {
    @InjectMocks
    WeatherApiImpl weatherApi;
    @Mock
    private RestTemplate restTemplate;

    @Test
    void getWeatherDetails() {
        //given
        WeatherApiDto expectedApiDto = new WeatherApiDto("temperature", "wind", "description",
                List.of(new WeatherApiDto.ForecastDTO("1", "15", "20")));

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(WeatherApiDto.class)))
                .thenReturn(ResponseEntity.of(Optional.of(expectedApiDto)));

        //when
        WeatherApiDto actualApiDto = weatherApi.getWeatherDetails("Cluj-Napoca");

        //then
        assertThat(actualApiDto).isNotNull();
        assertThat(actualApiDto.getForecast()).hasSize(1);
        assertThat(actualApiDto.getForecast().get(0).getDay()).isEqualTo(expectedApiDto.getForecast().get(0).getDay());
        assertThat(actualApiDto.getForecast().get(0).getTemperature()).isEqualTo(expectedApiDto.getForecast().get(0).getTemperature());
        assertThat(actualApiDto.getForecast().get(0).getWind()).isEqualTo(expectedApiDto.getForecast().get(0).getWind());
    }
}