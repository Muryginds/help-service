package ru.t1.helpservice.repository;

import ru.t1.helpservice.entity.SupportPhrase;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class SupportPhraseRepositoryImpl implements SupportPhraseRepository {

    private static final Map<Long, SupportPhrase> PHRASES = new HashMap<>();
    private static final Random random = new Random();

    @Override
    public void save(SupportPhrase supportPhrase) {
        PHRASES.put(supportPhrase.getId(), supportPhrase);
    }

    @Override
    public Optional<SupportPhrase> getRandomPhrase() {
        if (PHRASES.isEmpty()) {
            return Optional.empty();
        }
        var id = random.nextLong(1, PHRASES.size() + 1L);
        return Optional.of(PHRASES.get(id));
    }
}
