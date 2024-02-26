package ru.t1.helpservice.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record SupportPhraseDto(
        @JsonProperty("phraseId")
        Long id,
        @JsonProperty("phraseText")
        String phrase
) {
}
