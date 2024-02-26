package ru.t1.helpservice.backend.repository;


import ru.t1.helpservice.backend.entity.SupportPhrase;

import java.util.Optional;

public interface SupportPhraseRepository {

    void save(SupportPhrase supportPhrase);

    Optional<SupportPhrase> getRandomPhrase();

    boolean checkExistsByName(String phrase);
}
