package com.example.weathercollector.service;

import com.example.weathercollector.entity.WeatherFailure;
import com.example.weathercollector.entity.WeatherFailureStatus;
import com.example.weathercollector.repository.WeatherFailureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WeatherFailureService {

    private final WeatherFailureRepository weatherFailureRepository;
    private final WeatherService weatherService;

    public WeatherFailureService(
            WeatherFailureRepository weatherFailureRepository,
            WeatherService weatherService
    ) {
        this.weatherFailureRepository = weatherFailureRepository;
        this.weatherService = weatherService;
    }

    @Transactional(readOnly = true)
    public List<WeatherFailure> findAll() {
        return weatherFailureRepository
                .findAllByOrderByFailedAtDesc();
    }

    @Transactional
    public WeatherFailure retry(
            Long failureId,
            String retryMode
    ) {
        WeatherFailure failure = weatherFailureRepository
                .findById(failureId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "실패 이력을 찾을 수 없습니다. id="
                                        + failureId
                        )
                );

        if (failure.getStatus()
                == WeatherFailureStatus.RECOVERED) {
            throw new IllegalStateException(
                    "이미 복구된 실패 이력입니다."
            );
        }

        weatherService.collect(retryMode);

        failure.recover();

        return failure;
    }
}