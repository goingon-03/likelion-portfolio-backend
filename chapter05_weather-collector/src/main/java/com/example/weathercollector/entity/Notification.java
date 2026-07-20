package com.example.weathercollector.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long weatherDataId;

    private String message;

    private LocalDateTime receivedAt;

    protected Notification() {
    }

    public Notification(
            Long weatherDataId,
            String message,
            LocalDateTime receivedAt
    ) {
        this.weatherDataId = weatherDataId;
        this.message = message;
        this.receivedAt = receivedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getWeatherDataId() {
        return weatherDataId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }
}