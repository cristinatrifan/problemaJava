package com.example.problemajava.api;

import com.example.problemajava.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
class WeatherApiImpl implements WeatherApi{
    private final RestTemplate restTemplate;
    @Value("${weather.api.url}")
    private String apiUrl;

    public WeatherApiDto getWeatherDetails(String city) {
        log.info("Fetching data from API for city {}.", city);

        String completeApiCallUrl = apiUrl + "/" + city;
        try {
            ResponseEntity<WeatherApiDto> response = restTemplate.exchange(completeApiCallUrl, HttpMethod.GET, null, WeatherApiDto.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new BusinessException("No API data found for city " + city);
        }

    }

}
