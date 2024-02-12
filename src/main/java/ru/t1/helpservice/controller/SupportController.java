package ru.t1.helpservice.controller;

import lombok.Setter;
import ru.t1.helpservice.dto.BaseResponseDTO;
import ru.t1.helpservice.dto.SupportPhraseRequestDTO;
import ru.t1.helpservice.dto.SupportPhraseResponseDTO;
import ru.t1.helpservice.service.SupportPhraseService;

@Setter
public class SupportController {

    private SupportPhraseService supportPhraseService;

    //@PostMappig("/support")
    public BaseResponseDTO addSupportPhrase(SupportPhraseRequestDTO request) {
        return null;
    }

    //@GetMappig("/support")
    public SupportPhraseResponseDTO getSupportPhrase() {
        return null;
    }
}
