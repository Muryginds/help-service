package ru.t1.helpservice.broker.subscriber;

import lombok.RequiredArgsConstructor;
import ru.t1.helpservice.broker.queue.MessageQueue;

@RequiredArgsConstructor
public class PhraseSubscriber implements Subscriber {
    private final MessageQueue messageQueue;

    @Override
    public String processMessage() {
        return messageQueue.poll();
    }
}
