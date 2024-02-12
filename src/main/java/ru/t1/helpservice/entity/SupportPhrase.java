package ru.t1.helpservice.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;

@Builder
@Getter
public class SupportPhrase {
    private static AtomicLong counter = new AtomicLong(1);

    @Builder.Default
    private Long id = counter.getAndIncrement();
    private String message;
}
