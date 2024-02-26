package ru.t1.helpservice.backend.dto;

import lombok.Builder;

@Builder
public record BaseResponseDto(
        String message
) {
}
