package com.example.weathercollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WeatherCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                WeatherCollectorApplication.class,
                args
        );
    }
}