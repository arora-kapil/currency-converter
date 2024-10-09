package com.converter.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ApiResponseDto {
    private int statusCode;
    private String message;
}
