package ru.t1.helpservice.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.t1.helpservice.backend.dto.SupportPhraseRequestDto;
import ru.t1.helpservice.backend.entity.SupportPhrase;
import ru.t1.helpservice.backend.exception.SupportPhraseException;
import ru.t1.helpservice.backend.exception.SupportPhraseNotFoundException;
import ru.t1.helpservice.backend.repository.SupportPhraseRepository;
import ru.t1.helpservice.backend.service.SupportPhraseService;

@Service
@RequiredArgsConstructor
public class SupportPhraseServiceImpl implements SupportPhraseService {
    private final SupportPhraseRepository supportPhraseRepository;

    @Override
    public void save(SupportPhraseRequestDto supportPhraseRequestDto) {
        var newPhrase = supportPhraseRequestDto.phrase();
        if (newPhrase == null || newPhrase.isBlank()) {
            throw new SupportPhraseException("Parameter 'phrase' must not be empty");
        }
        if (supportPhraseRepository.checkExistsByName(newPhrase)) {
            throw new SupportPhraseException("Phrase already exist");
        }
        var newSupportPhrase = SupportPhrase.builder().message(newPhrase).build();
        supportPhraseRepository.save(newSupportPhrase);
    }

    @Override
    public SupportPhrase getRandomPhrase() {
        return supportPhraseRepository.getRandomPhrase()
                .orElseThrow(SupportPhraseNotFoundException::new);
    }
}
