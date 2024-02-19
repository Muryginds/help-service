package ru.t1.helpservice.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SupportPhraseRequestDTO(
        @JsonProperty("phrase")
        String phrase
) {
}
