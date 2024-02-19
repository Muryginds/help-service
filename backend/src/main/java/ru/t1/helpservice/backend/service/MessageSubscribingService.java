package ru.t1.helpservice.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.t1.helpservice.backend.annotation.Subscribe;
import ru.t1.helpservice.broker.subscriber.Subscriber;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MessageSubscribingService implements Runnable {
    private final SupportPhraseService supportPhraseService;
    private final Subscriber subscriber;

    @Subscribe
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            var message = subscriber.processMessage();
            if (Objects.nonNull(message)) {
                supportPhraseService.save(message);
            } else {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
