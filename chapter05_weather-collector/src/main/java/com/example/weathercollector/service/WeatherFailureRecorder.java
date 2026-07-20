package com.example.weathercollector.service;

import com.example.weathercollector.entity.WeatherFailure;
import com.example.weathercollector.entity.WeatherFailureStatus;
import com.example.weathercollector.repository.WeatherFailureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class WeatherFailureRecorder {

    private final WeatherFailureRepository weatherFailureRepository;

    public WeatherFailureRecorder(
            WeatherFailureRepository weatherFailureRepository
    ) {
        this.weatherFailureRepository = weatherFailureRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public WeatherFailure record(
            String mode,
            Exception exception
    ) {
        String errorMessage = getSimpleMessage(exception);

        WeatherFailure failure = new WeatherFailure(
                mode,
                errorMessage,
                WeatherFailureStatus.FAILED,
                LocalDateTime.now()
        );

        WeatherFailure savedFailure =
                weatherFailureRepository.save(failure);

        System.out.println(
                "[WeatherFailureRecorder] 실패 이력 저장, id="
                        + savedFailure.getId()
                        + ", mode="
                        + mode
        );

        return savedFailure;
    }

    private String getSimpleMessage(Exception exception) {
        String message = exception.getMessage();

        if (message == null || message.isBlank()) {
            return exception.getClass().getSimpleName();
        }

        if (message.length() > 300) {
            return message.substring(0, 300);
        }

        return message;
    }
}