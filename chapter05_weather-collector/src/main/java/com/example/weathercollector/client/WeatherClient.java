package com.example.weathercollector.client;

import com.example.weathercollector.dto.WeatherApiResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Component
public class WeatherClient {

    private static final int MAX_ATTEMPTS = 3;

    private final RestClient restClient;

    public WeatherClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public WeatherApiResponse getCurrentWeather(String mode) {
        int attempt = 1;

        while (true) {
            try {
                System.out.println(
                        "[WeatherClient] API 호출 시도: "
                                + attempt
                                + "/"
                                + MAX_ATTEMPTS
                                + ", mode="
                                + mode
                );

                return restClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/test-api/weather")
                                .queryParam("mode", mode)
                                .build())
                        .retrieve()
                        .body(WeatherApiResponse.class);

            } catch (HttpClientErrorException.BadRequest exception) {
                System.out.println(
                        "[WeatherClient] 400 오류 - 재시도하지 않습니다."
                );

                throw exception;

            } catch (
                    ResourceAccessException
                    | HttpServerErrorException.ServiceUnavailable exception
            ) {
                System.out.println(
                        "[WeatherClient] 재시도 가능한 오류 발생: "
                                + exception.getClass().getSimpleName()
                );

                if (attempt >= MAX_ATTEMPTS) {
                    System.out.println(
                            "[WeatherClient] 최대 재시도 횟수에 도달했습니다."
                    );

                    throw exception;
                }

                attempt++;
            }
        }
    }
}