package com.example.problemajava.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RestTemplate {
    @Bean
    public org.springframework.web.client.RestTemplate getRestTemplate() {
        return new org.springframework.web.client.RestTemplate();
    }
}
