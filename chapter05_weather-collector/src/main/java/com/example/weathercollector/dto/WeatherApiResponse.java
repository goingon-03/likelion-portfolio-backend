package com.example.weathercollector.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherApiResponse(

        double latitude, //위도
        double longitude, //경도
        String timezone,
        Current current

) {

    public record Current(

            String time,

            @JsonProperty("temperature_2m")
            double temperature,

            @JsonProperty("relative_humidity_2m")
            int humidity,

            double precipitation

    ) {
    }

}