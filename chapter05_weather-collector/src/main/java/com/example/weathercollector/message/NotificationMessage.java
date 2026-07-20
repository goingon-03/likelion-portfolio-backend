package com.example.weathercollector.message;

import java.time.LocalDateTime;

public record NotificationMessage(
        Long weatherDataId,
        double temperature,
        int humidity,
        double precipitation,
        LocalDateTime weatherTime,
        LocalDateTime publishedAt
) {
}