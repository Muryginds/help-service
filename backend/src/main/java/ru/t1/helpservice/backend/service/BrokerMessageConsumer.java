package ru.t1.helpservice.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import ru.t1.helpservice.backend.dto.SupportPhraseRequestDto;
import ru.t1.helpservice.backend.entity.SupportPhrase;
import ru.t1.helpservice.backend.repository.SupportPhraseRepository;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class BrokerMessageConsumer extends AbstractConsumerSeekAware {
    private final SupportPhraseRepository supportPhraseRepository;

    @KafkaListener(topics = "${spring.kafka.topic-name}")
    public void listen(SupportPhraseRequestDto request) {
        var message = request.phrase();
        log.info("Received new message from kafka: {}", request);
        if (supportPhraseRepository.checkExistsByName(message)) {
            log.warn("Phrase already exist: {}", message);
        } else {
            log.info("Phrase '{}' from kafka saved to repository", message);
            var phrase = SupportPhrase.builder().message(message).build();
            supportPhraseRepository.save(phrase);
        }
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        callback.seekToBeginning(assignments.keySet());
    }
}
