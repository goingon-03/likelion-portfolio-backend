package com.example.externalapi.exception;

public class ExternalApiTimeoutException extends RuntimeException {

    public ExternalApiTimeoutException() {
        super("외부 API 응답 시간이 초과되었습니다.");
    }
}