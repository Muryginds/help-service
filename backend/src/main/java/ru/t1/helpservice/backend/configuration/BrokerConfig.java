package ru.t1.helpservice.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.t1.helpservice.broker.publisher.PhrasePublisher;
import ru.t1.helpservice.broker.publisher.Publisher;
import ru.t1.helpservice.broker.queue.InMemoryMessageQueue;
import ru.t1.helpservice.broker.queue.MessageQueue;
import ru.t1.helpservice.broker.runner.ConsumingMethodRunner;
import ru.t1.helpservice.broker.subscriber.PhraseSubscriber;
import ru.t1.helpservice.broker.subscriber.Subscriber;

@Configuration
public class BrokerConfig {

    @Bean
    public MessageQueue messageQueue() {
        return new InMemoryMessageQueue();
    }

    @Bean
    public Subscriber subscriber(MessageQueue messageQueue) {
        return new PhraseSubscriber(messageQueue);
    }

    @Bean
    public Publisher publisher(MessageQueue messageQueue) {
        return new PhrasePublisher(messageQueue);
    }

    @Bean
    public ConsumingMethodRunner consumingMethodRunner() {
        return new ConsumingMethodRunner();
    }
}
