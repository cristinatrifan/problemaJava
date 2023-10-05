package com.example.problemajava.forecast;

enum ForecastedCities {
    CLUJNAPOCA("Cluj-Napoca"),
    BUCURESTI("Bucuresti"),
    TIMISOARA("Timisoara"),
    CONSTANTA("Constanta"),
    BAIAMARE("Baia-Mare"),
    ARAD("Arad");

    private final String cityString;

    ForecastedCities(String city) {
        this.cityString = city;
    }

    public String getCityString() {
        return this.cityString;
    }
}
