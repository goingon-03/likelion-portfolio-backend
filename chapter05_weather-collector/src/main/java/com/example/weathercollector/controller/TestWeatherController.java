package com.example.weathercollector.controller;

import com.example.weathercollector.dto.WeatherApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/test-api")
public class TestWeatherController {

    @GetMapping("/weather")
    public WeatherApiResponse getWeather(
            @RequestParam(defaultValue = "success") String mode
    ) throws InterruptedException {

        return switch (mode) {
            case "success" -> createSuccessResponse();

            case "delay" -> {
                Thread.sleep(5000);
                yield createSuccessResponse();
            }

            case "unavailable" ->
                    throw new ResponseStatusException(
                            HttpStatus.SERVICE_UNAVAILABLE,
                            "테스트용 503 오류입니다."
                    );

            case "bad-request" ->
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "테스트용 400 오류입니다."
                    );

            default ->
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "지원하지 않는 mode입니다."
                    );
        };
    }

    private WeatherApiResponse createSuccessResponse() {
        return new WeatherApiResponse(
                37.5665,
                126.9780,
                "Asia/Seoul",
                new WeatherApiResponse.Current(
                        LocalDateTime.now()
                                .withSecond(0)
                                .withNano(0)
                                .toString(),
                        25.3,
                        70,
                        0.0
                )
        );
    }
}