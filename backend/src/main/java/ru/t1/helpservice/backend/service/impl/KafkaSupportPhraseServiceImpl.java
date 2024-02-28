package ru.t1.helpservice.backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import ru.t1.helpservice.backend.configuration.properties.KafkaProperties;
import ru.t1.helpservice.backend.dto.SupportPhraseRequestDto;
import ru.t1.helpservice.backend.entity.SupportPhrase;
import ru.t1.helpservice.backend.exception.SupportPhraseException;
import ru.t1.helpservice.backend.exception.SupportPhraseNotFoundException;
import ru.t1.helpservice.backend.repository.SupportPhraseRepository;
import ru.t1.helpservice.backend.service.SupportPhraseService;

@Slf4j
@RequiredArgsConstructor
public class KafkaSupportPhraseServiceImpl implements SupportPhraseService {
    private final SupportPhraseRepository supportPhraseRepository;
    private final KafkaTemplate<String, SupportPhraseRequestDto> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    @Override
    public void save(SupportPhraseRequestDto supportPhraseRequestDto) {
        var newPhrase = supportPhraseRequestDto.phrase();
        if (supportPhraseRepository.checkExistsByName(newPhrase)) {
            throw new SupportPhraseException("Phrase '%s' already exist".formatted(newPhrase));
        }
        var newSupportPhrase = SupportPhrase.builder().message(newPhrase).build();
        supportPhraseRepository.save(newSupportPhrase);
        log.info("Phrase '{}' saved to repository", newPhrase);

        kafkaTemplate.send(kafkaProperties.topicName(), supportPhraseRequestDto);
        log.info("Phrase '{}' sent to kafka", newPhrase);
    }

    @Override
    public SupportPhrase getRandomPhrase() {
        return supportPhraseRepository.getRandomPhrase()
                .orElseThrow(SupportPhraseNotFoundException::new);
    }
}
