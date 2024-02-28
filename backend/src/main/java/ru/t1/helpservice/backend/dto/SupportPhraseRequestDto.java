package ru.t1.helpservice.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record SupportPhraseRequestDto(
        @JsonProperty("phrase")
        @NotBlank
        String phrase
) {
}
