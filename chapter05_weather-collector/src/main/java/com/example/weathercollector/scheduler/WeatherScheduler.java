package com.example.weathercollector.scheduler;

import com.example.weathercollector.entity.WeatherData;
import com.example.weathercollector.service.WeatherService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class WeatherScheduler {

    private final WeatherService weatherService;

    public WeatherScheduler(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Scheduled(
            cron = "${weather.scheduler.cron}",
            zone = "${weather.scheduler.zone}"
    )
    public void collectWeather() {
        System.out.println(
                "[WeatherScheduler] 날씨 자동 수집 시작: "
                        + LocalDateTime.now()
        );

        try {
            WeatherData weatherData =
                    weatherService.collect("success");

            System.out.println(
                    "[WeatherScheduler] 날씨 자동 수집 성공"
                            + ", id="
                            + weatherData.getId()
                            + ", collectedAt="
                            + weatherData.getCollectedAt()
            );

        } catch (Exception exception) {
            System.out.println(
                    "[WeatherScheduler] 날씨 자동 수집 최종 실패: "
                            + exception.getMessage()
            );
        }
    }
}