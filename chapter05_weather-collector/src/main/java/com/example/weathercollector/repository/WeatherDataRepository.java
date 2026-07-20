package com.example.weathercollector.repository;

import com.example.weathercollector.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherDataRepository
        extends JpaRepository<WeatherData, Long> {
}

//WeatherData → 어떤 엔티티를 관리할지
//Long → 엔티티의 id 타입