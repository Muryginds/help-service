package ru.t1.helpservice.service;

import lombok.RequiredArgsConstructor;
import ru.t1.helpservice.entity.SupportPhrase;
import ru.t1.helpservice.exception.SupportPhraseException;
import ru.t1.helpservice.exception.SupportPhraseNotFoundException;
import ru.t1.helpservice.repository.SupportPhraseRepository;

@RequiredArgsConstructor
public class SupportPhraseService {
    private  final SupportPhraseRepository supportPhraseRepository;

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
