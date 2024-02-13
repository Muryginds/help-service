package ru.t1.helpservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ErrorDTO(
        @JsonProperty("error")
        String error
) {
}
