package ru.t1.helpservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SupportPhraseRequestDTO(
        @JsonProperty("phrase")
        String phrase
) {
}
