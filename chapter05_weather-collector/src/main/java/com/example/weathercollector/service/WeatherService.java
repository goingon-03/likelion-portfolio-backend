package com.example.weathercollector.service;

import com.example.weathercollector.client.WeatherClient;
import com.example.weathercollector.dto.WeatherApiResponse;
import com.example.weathercollector.entity.WeatherData;
import com.example.weathercollector.message.NotificationMessage;
import com.example.weathercollector.message.NotificationPublisher;
import com.example.weathercollector.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class WeatherService {

    private final WeatherClient weatherClient;
    private final WeatherDataRepository weatherDataRepository;
    private final NotificationPublisher notificationPublisher;
    private final WeatherFailureRecorder weatherFailureRecorder;

    public WeatherService(
            WeatherClient weatherClient,
            WeatherDataRepository weatherDataRepository,
            NotificationPublisher notificationPublisher,
            WeatherFailureRecorder weatherFailureRecorder
    ) {
        this.weatherClient = weatherClient;
        this.weatherDataRepository = weatherDataRepository;
        this.notificationPublisher = notificationPublisher;
        this.weatherFailureRecorder = weatherFailureRecorder;
    }

    @Transactional
    public WeatherData collect(String mode) {
        try {
            WeatherApiResponse response =
                    weatherClient.getCurrentWeather(mode);

            if (response == null || response.current() == null) {
                throw new IllegalStateException(
                        "날씨 API 응답이 비어 있습니다."
                );
            }

            WeatherApiResponse.Current current = response.current();

            WeatherData weatherData = new WeatherData(
                    response.latitude(),
                    response.longitude(),
                    current.temperature(),
                    current.humidity(),
                    current.precipitation(),
                    LocalDateTime.parse(current.time())
            );

            WeatherData savedWeatherData =
                    weatherDataRepository.save(weatherData);

            NotificationMessage notificationMessage =
                    new NotificationMessage(
                            savedWeatherData.getId(),
                            savedWeatherData.getTemperature(),
                            savedWeatherData.getHumidity(),
                            savedWeatherData.getPrecipitation(),
                            savedWeatherData.getWeatherTime(),
                            LocalDateTime.now()
                    );

            notificationPublisher.publish(notificationMessage);

            return savedWeatherData;

        } catch (Exception exception) {
            weatherFailureRecorder.record(mode, exception);
            throw exception;
        }
    }
}