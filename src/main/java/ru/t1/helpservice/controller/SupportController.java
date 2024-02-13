package ru.t1.helpservice.controller;

import ru.t1.helpservice.annotation.Autowired;
import ru.t1.helpservice.annotation.Controller;
import ru.t1.helpservice.annotation.GetMapping;
import ru.t1.helpservice.annotation.PostMapping;
import ru.t1.helpservice.dto.BaseResponseDTO;
import ru.t1.helpservice.dto.SupportPhraseDTO;
import ru.t1.helpservice.dto.SupportPhraseRequestDTO;
import ru.t1.helpservice.service.SupportPhraseService;

@Controller
public class SupportController {
    private SupportPhraseService supportPhraseService;

    @Autowired
    public void setSupportPhraseService(SupportPhraseService supportPhraseService) {
        this.supportPhraseService = supportPhraseService;
    }

    @PostMapping(path = "/api/v1/support")
    public BaseResponseDTO addSupportPhrase(SupportPhraseRequestDTO request) {
        var phrase = request.phrase();
        supportPhraseService.save(phrase);
        return BaseResponseDTO.builder().message("New phrase saved").build();
    }

    @GetMapping(path = "/api/v1/support")
    public SupportPhraseDTO getSupportPhrase() {
        var supportPhrase = supportPhraseService.getRandomPhrase();
        return SupportPhraseDTO.builder()
                .id(supportPhrase.getId())
                .phrase(supportPhrase.getMessage())
                .build();
    }
}
