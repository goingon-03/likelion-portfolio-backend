package com.example.externalapi.controller;

import com.example.externalapi.dto.PostResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class TestExternalApiController {

    @GetMapping("/test-api/delay")
    public PostResponse delay() throws InterruptedException {

        Thread.sleep(5000);

        return new PostResponse(
                1L,
                1L,
                "지연 응답",
                "5초 후에 반환된 응답입니다."
        );
    }

    private int serviceUnavailableCount = 0;

    @GetMapping("/test-api/503")
    public PostResponse serviceUnavailable(
            HttpServletResponse response
    ) {

        serviceUnavailableCount++;

        if (serviceUnavailableCount <= 2) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return null;
        }

        return new PostResponse(
                1L,
                503L,
                "재시도 성공",
                "세 번째 요청에서 정상 응답했습니다."
        );
    }

    @GetMapping("/test-api/400")
    public void badRequest(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}