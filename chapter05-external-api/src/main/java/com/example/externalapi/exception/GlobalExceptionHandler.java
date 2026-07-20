package com.example.externalapi.exception;

import com.example.externalapi.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFound(
            PostNotFoundException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(ExternalApiTimeoutException.class)
    public ResponseEntity<ErrorResponse> handleExternalApiTimeout(
            ExternalApiTimeoutException exception
    ) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.GATEWAY_TIMEOUT.value(),
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.GATEWAY_TIMEOUT)
                .body(response);
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ErrorResponse> handleExternalApiException(
            ExternalApiException exception
    ) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_GATEWAY.value(),
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(response);
    }
}