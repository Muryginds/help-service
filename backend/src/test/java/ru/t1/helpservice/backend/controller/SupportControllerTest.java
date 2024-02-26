package ru.t1.helpservice.backend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.t1.helpservice.backend.dto.SupportPhraseDto;
import ru.t1.helpservice.backend.dto.SupportPhraseRequestDto;
import ru.t1.helpservice.backend.entity.SupportPhrase;
import ru.t1.helpservice.backend.service.SupportPhraseService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupportControllerTest {

    @Mock
    private SupportPhraseService supportPhraseService;

    @InjectMocks
    private SupportController supportController;

    @Test
    void testAddSupportPhrase() {
        var phrase = "Test support phrase";
        var requestDTO = new SupportPhraseRequestDto(phrase);

        doNothing().when(supportPhraseService).save(requestDTO);

        supportController.addSupportPhrase(requestDTO);

        verify(supportPhraseService, times(1)).save(requestDTO);
    }

    @Test
    void testGetSupportPhrase() {
        var phrase = "Test support phrase";
        var id = 1L;

        var supportPhrase = SupportPhrase.builder().id(id).message(phrase).build();
        var expectedDTO = SupportPhraseDto.builder().id(id).phrase(phrase).build();

        when(supportPhraseService.getRandomPhrase()).thenReturn(supportPhrase);

        var responseDTO = supportController.getSupportPhrase();

        verify(supportPhraseService, times(1)).getRandomPhrase();
        assertEquals(expectedDTO, responseDTO);
    }
}
