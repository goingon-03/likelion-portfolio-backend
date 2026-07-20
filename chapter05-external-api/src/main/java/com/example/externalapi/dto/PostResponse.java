package com.example.externalapi.dto;

public record PostResponse (
    Long userId,
    Long id,
    String title,
    String body
) {

}
// 외부 API가 보내준 데이터 담는 용도라 record 사용
//PostResponde response라고 하면 response.id();처럼 값을 꺼내올수있음