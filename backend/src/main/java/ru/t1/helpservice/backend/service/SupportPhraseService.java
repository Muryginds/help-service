package ru.t1.helpservice.backend.service;

import ru.t1.helpservice.backend.dto.SupportPhraseRequestDto;
import ru.t1.helpservice.backend.entity.SupportPhrase;

public interface SupportPhraseService {

    void save(SupportPhraseRequestDto supportPhraseRequestDto);

    SupportPhrase getRandomPhrase();
}
