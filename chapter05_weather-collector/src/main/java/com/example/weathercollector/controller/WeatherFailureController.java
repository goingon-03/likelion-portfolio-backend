package com.example.weathercollector.controller;

import com.example.weathercollector.entity.WeatherFailure;
import com.example.weathercollector.service.WeatherFailureService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather/failures")
public class WeatherFailureController {

    private final WeatherFailureService weatherFailureService;

    public WeatherFailureController(
            WeatherFailureService weatherFailureService
    ) {
        this.weatherFailureService = weatherFailureService;
    }

    @GetMapping
    public List<WeatherFailure> findAll() {
        return weatherFailureService.findAll();
    }

    @PostMapping("/{failureId}/retry")
    public WeatherFailure retry(
            @PathVariable Long failureId,
            @RequestParam(defaultValue = "success")
            String mode
    ) {
        return weatherFailureService.retry(
                failureId,
                mode
        );
    }
}