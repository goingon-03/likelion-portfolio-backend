package com.example.weathercollector.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity //이 클래스를 JPA 엔티티로 사용한다
@Table(name = "weather_data") //MySQL에 weather_data라는 이름으로 테이블이 만들어짐
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;

    private double longitude;

    private double temperature;

    private int humidity;

    private double precipitation;

    private LocalDateTime weatherTime;

    private LocalDateTime collectedAt;

    protected WeatherData() {//JPA가 객체를 생성할 때 필요해서 넣는 기본 생성자
    }

    public WeatherData(
            double latitude,
            double longitude,
            double temperature,
            int humidity,
            double precipitation,
            LocalDateTime weatherTime
    ) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
        this.humidity = humidity;
        this.precipitation = precipitation;
        this.weatherTime = weatherTime;//날씨 API가 알려준 기준 시각
        this.collectedAt = LocalDateTime.now();
        //우리 서버가 실제로 저장한 시각
    }

    public Long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public LocalDateTime getWeatherTime() {
        return weatherTime;
    }

    public LocalDateTime getCollectedAt() {
        return collectedAt;
    }
}