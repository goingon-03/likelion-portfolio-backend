package com.example.weathercollector.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "weather_failure")
public class WeatherFailure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mode;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @Enumerated(EnumType.STRING)
    private WeatherFailureStatus status;

    private LocalDateTime failedAt;

    private LocalDateTime retriedAt;

    protected WeatherFailure() {
    }

    public WeatherFailure(
            String mode,
            String errorMessage,
            WeatherFailureStatus status,
            LocalDateTime failedAt
    ) {
        this.mode = mode;
        this.errorMessage = errorMessage;
        this.status = status;
        this.failedAt = failedAt;
    }

    public void recover() {
        this.status = WeatherFailureStatus.RECOVERED;
        this.retriedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getMode() {
        return mode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public WeatherFailureStatus getStatus() {
        return status;
    }

    public LocalDateTime getFailedAt() {
        return failedAt;
    }

    public LocalDateTime getRetriedAt() {
        return retriedAt;
    }
}