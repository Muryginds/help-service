package ru.t1.helpservice.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupportPhrase {
    private static Long counter = 1L;

    @Builder.Default
    private Long id = counter++;
    private String message;
}
