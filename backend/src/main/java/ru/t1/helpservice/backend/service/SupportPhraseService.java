package ru.t1.helpservice.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.t1.helpservice.backend.entity.SupportPhrase;
import ru.t1.helpservice.backend.exception.SupportPhraseException;
import ru.t1.helpservice.backend.exception.SupportPhraseNotFoundException;
import ru.t1.helpservice.backend.repository.SupportPhraseRepository;

@Service
@RequiredArgsConstructor
public class SupportPhraseService {
    private final SupportPhraseRepository supportPhraseRepository;

    public void save(String newPhrase) {
        if (newPhrase == null || newPhrase.isBlank()) {
            throw new SupportPhraseException("Parameter 'phrase' must not be empty");
        }

        var newSupportPhrase = SupportPhrase.builder().message(newPhrase).build();
        supportPhraseRepository.save(newSupportPhrase);
    }

    public SupportPhrase getRandomPhrase() {
        return supportPhraseRepository.getRandomPhrase()
                .orElseThrow(SupportPhraseNotFoundException::new);
    }
}
