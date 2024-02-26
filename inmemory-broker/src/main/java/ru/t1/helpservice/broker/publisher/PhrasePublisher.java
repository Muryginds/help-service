package ru.t1.helpservice.broker.publisher;

import lombok.RequiredArgsConstructor;
import ru.t1.helpservice.broker.queue.MessageQueue;

@RequiredArgsConstructor
public class PhrasePublisher implements Publisher {
    private final MessageQueue messageQueue;

    @Override
    public void publishMessage(String message) {
        messageQueue.publish(message);
    }
}
