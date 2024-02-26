package ru.t1.helpservice.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SupportPhraseRequestDto(
        @JsonProperty("phrase")
        String phrase
) {
}
