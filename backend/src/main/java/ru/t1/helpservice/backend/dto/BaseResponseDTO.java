package ru.t1.helpservice.backend.dto;

import lombok.Builder;

@Builder
public record BaseResponseDTO(
        String message
) {
}
