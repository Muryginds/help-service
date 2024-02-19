package ru.t1.helpservice.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.t1.helpservice.backend.dto.BaseResponseDTO;
import ru.t1.helpservice.backend.service.SupportPhraseService;
import ru.t1.helpservice.broker.publisher.Publisher;
import ru.t1.helpservice.backend.dto.SupportPhraseDTO;
import ru.t1.helpservice.backend.dto.SupportPhraseRequestDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/support")
public class SupportController {
    private final SupportPhraseService supportPhraseService;
    private final Publisher publisher;

    @PostMapping
    public BaseResponseDTO addSupportPhrase(@RequestBody SupportPhraseRequestDTO request) {
        var phrase = request.phrase();
        publisher.publishMessage(phrase);
        return BaseResponseDTO.builder().message("New phrase saved").build();
    }

    @GetMapping
    public SupportPhraseDTO getSupportPhrase() {
        var supportPhrase = supportPhraseService.getRandomPhrase();
        return SupportPhraseDTO.builder()
                .id(supportPhrase.getId())
                .phrase(supportPhrase.getMessage())
                .build();
    }
}
