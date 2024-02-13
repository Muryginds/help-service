package ru.t1.helpservice.dto;

import lombok.Builder;

@Builder
public record BaseResponseDTO(
        String message
) {
}
