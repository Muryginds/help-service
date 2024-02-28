package ru.t1.helpservice.backend.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka")
public record KafkaProperties(
        String bootstrapServers,
        String topicName,
        Consumer consumer
) {
    public record Consumer(
            String groupId
    ) {
    }
}
