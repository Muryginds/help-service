package ru.t1.helpservice.repository;

import ru.t1.helpservice.entity.SupportPhrase;

import java.util.Optional;

public interface SupportPhraseRepository {

    void save(SupportPhrase supportPhrase);

    Optional<SupportPhrase> getRandomPhrase();
}
