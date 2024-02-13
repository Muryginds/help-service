package ru.t1.helpservice.repository;

import ru.t1.helpservice.entity.SupportPhrase;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class SupportPhraseRepositoryImpl implements SupportPhraseRepository {
    private static final Map<Long, SupportPhrase> PHRASES = new ConcurrentHashMap<>();
    private static final Random random = new Random();

    @Override
    public void save(SupportPhrase supportPhrase) {
        PHRASES.put(supportPhrase.getId(), supportPhrase);
    }

    @Override
    public synchronized Optional<SupportPhrase> getRandomPhrase() {
        var mapSize = PHRASES.size();
        if (mapSize == 0) {
            return Optional.empty();
        }
        var keyList = new ArrayList<>(PHRASES.keySet());
        var indexInList = random.nextInt(keyList.size());
        var id = keyList.get(indexInList);
        return Optional.of(PHRASES.get(id));
    }
}
