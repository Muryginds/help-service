package ru.t1.helpservice.backend.repository;

import org.springframework.stereotype.Repository;
import ru.t1.helpservice.backend.entity.SupportPhrase;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SupportPhraseRepositoryImpl implements SupportPhraseRepository {
    private static final Map<Long, SupportPhrase> PHRASES = init();
    private static final Random random = new Random();

    private static Map<Long, SupportPhrase> init() {
        var map = new ConcurrentHashMap<Long, SupportPhrase>();
        var supportPhrase = SupportPhrase.builder().message("Default support phrase").build();
        map.put(supportPhrase.getId(), supportPhrase);
        return map;
    }

    @Override
    public void save(SupportPhrase supportPhrase) {
        PHRASES.put(supportPhrase.getId(), supportPhrase);
    }

    @Override
    public Optional<SupportPhrase> getRandomPhrase() {
        if (PHRASES.isEmpty()) {
            return Optional.empty();
        }
        var keyList = new ArrayList<>(PHRASES.keySet());
        var indexInList = random.nextInt(keyList.size());
        var id = keyList.get(indexInList);
        return Optional.of(PHRASES.get(id));
    }
}
