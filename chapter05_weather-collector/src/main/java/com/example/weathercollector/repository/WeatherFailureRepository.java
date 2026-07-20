package com.example.weathercollector.repository;

import com.example.weathercollector.entity.WeatherFailure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherFailureRepository
        extends JpaRepository<WeatherFailure, Long> {

    List<WeatherFailure> findAllByOrderByFailedAtDesc();
}