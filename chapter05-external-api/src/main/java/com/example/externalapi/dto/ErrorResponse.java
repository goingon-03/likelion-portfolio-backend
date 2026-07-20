package com.example.externalapi.dto;

public record ErrorResponse(
        int status,
        String message
) {
}