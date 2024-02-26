package ru.t1.helpservice.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.t1.helpservice.backend.dto.BaseResponseDto;
import ru.t1.helpservice.backend.dto.SupportPhraseDto;
import ru.t1.helpservice.backend.dto.SupportPhraseRequestDto;
import ru.t1.helpservice.backend.service.SupportPhraseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/support")
public class SupportController {
    private final SupportPhraseService supportPhraseService;

    @PostMapping
    public BaseResponseDto addSupportPhrase(@RequestBody SupportPhraseRequestDto request) {
        supportPhraseService.save(request);
        return BaseResponseDto.builder().message("New phrase saved").build();
    }

    @GetMapping
    public SupportPhraseDto getSupportPhrase() {
        var supportPhrase = supportPhraseService.getRandomPhrase();
        return SupportPhraseDto.builder()
                .id(supportPhrase.getId())
                .phrase(supportPhrase.getMessage())
                .build();
    }
}
