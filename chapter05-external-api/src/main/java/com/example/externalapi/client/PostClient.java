package com.example.externalapi.client;

import com.example.externalapi.dto.PostResponse;
import com.example.externalapi.exception.ExternalApiException;
import com.example.externalapi.exception.ExternalApiTimeoutException;
import com.example.externalapi.exception.PostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.HttpClientSettings;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Slf4j
@Component
public class PostClient {

    private final RestClient postRestClient;
    private final RestClient testRestClient;

    public PostClient() {

        HttpClientSettings settings = HttpClientSettings.defaults()
                .withConnectTimeout(Duration.ofSeconds(2))
                .withReadTimeout(Duration.ofSeconds(2));

        ClientHttpRequestFactory requestFactory =
                ClientHttpRequestFactoryBuilder.detect()
                        .build(settings);

        this.postRestClient = RestClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .requestFactory(requestFactory)
                .build();

        this.testRestClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .requestFactory(requestFactory)
                .build();
    }

    public PostResponse getPost(Long postId) {

        log.info("외부 API 호출 시작: postId={}", postId);

        PostResponse response = postRestClient.get()
                .uri("/posts/{postId}", postId)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        (request, externalResponse) -> {
                            throw new PostNotFoundException(postId);
                        }
                )
                .body(PostResponse.class);

        log.info("외부 API 호출 완료: postId={}", postId);

        return response;
    }

    public PostResponse getDelayedResponse() {

        log.info("지연 API 호출 시작");

        try {
            PostResponse response = testRestClient.get()
                    .uri("/test-api/delay")
                    .retrieve()
                    .body(PostResponse.class);

            log.info("지연 API 호출 완료");

            return response;

        } catch (ResourceAccessException exception) {

            log.error("외부 API 호출 시간 초과", exception);

            throw new ExternalApiTimeoutException();
        }
    }

    public PostResponse getPostWithRetry() {

        int maxAttempts = 3;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {

            try {
                log.info("503 테스트 API 호출: attempt={}", attempt);

                return testRestClient.get()
                        .uri("/test-api/503")
                        .retrieve()
                        .body(PostResponse.class);

            } catch (HttpServerErrorException.ServiceUnavailable exception) {

                log.warn(
                        "503 응답 발생: attempt={}/{}",
                        attempt,
                        maxAttempts
                );

                if (attempt == maxAttempts) {
                    throw new ExternalApiException(
                            "외부 API 재시도에 모두 실패했습니다."
                    );
                }
            }
        }

        throw new ExternalApiException(
                "외부 API 호출에 실패했습니다."
        );
    }

    public void callBadRequestApi() {

        log.info("400 테스트 API 호출");

        try {
            testRestClient.get()
                    .uri("/test-api/400")
                    .retrieve()
                    .toBodilessEntity();

        } catch (HttpClientErrorException.BadRequest exception) {

            log.warn("400 응답 발생 - 재시도하지 않음");

            throw new ExternalApiException(
                    "잘못된 요청으로 외부 API 호출에 실패했습니다."
            );
        }
    }
}