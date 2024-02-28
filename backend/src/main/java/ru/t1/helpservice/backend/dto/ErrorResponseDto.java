package ru.t1.helpservice.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record ErrorResponseDto(
        @JsonProperty("errors")
        List<String> errors
) {
}
