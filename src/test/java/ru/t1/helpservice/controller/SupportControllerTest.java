package ru.t1.helpservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.t1.helpservice.dto.BaseResponseDTO;
import ru.t1.helpservice.dto.SupportPhraseDTO;
import ru.t1.helpservice.dto.SupportPhraseRequestDTO;
import ru.t1.helpservice.entity.SupportPhrase;
import ru.t1.helpservice.service.SupportPhraseService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SupportControllerTest {

    private SupportController supportController;
    private SupportPhraseService supportPhraseService;

    @BeforeEach
    void setUp() {
        supportPhraseService = mock(SupportPhraseService.class);
        supportController = new SupportController();
        supportController.setSupportPhraseService(supportPhraseService);
    }

    @Test
    void testAddSupportPhrase() {
        String phrase = "Test support phrase";
        SupportPhraseRequestDTO requestDTO = new SupportPhraseRequestDTO(phrase);

        BaseResponseDTO expectedResponse = BaseResponseDTO.builder().message("New phrase saved").build();

        doNothing().when(supportPhraseService).save(phrase);

        BaseResponseDTO responseDTO = supportController.addSupportPhrase(requestDTO);

        verify(supportPhraseService, times(1)).save(phrase);
        assertEquals(expectedResponse, responseDTO);
    }

    @Test
    void testGetSupportPhrase() {
        String phrase = "Test support phrase";
        long id = 1L;

        SupportPhraseDTO expectedDTO = SupportPhraseDTO.builder().id(id).phrase(phrase).build();

        when(supportPhraseService.getRandomPhrase()).thenReturn(SupportPhrase.builder().id(id).message(phrase).build());

        SupportPhraseDTO responseDTO = supportController.getSupportPhrase();

        verify(supportPhraseService, times(1)).getRandomPhrase();
        assertEquals(expectedDTO, responseDTO);
    }
}
