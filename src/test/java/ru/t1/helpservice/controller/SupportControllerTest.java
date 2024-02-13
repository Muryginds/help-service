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
        var phrase = "Test support phrase";
        var requestDTO = new SupportPhraseRequestDTO(phrase);

        var expectedResponse = BaseResponseDTO.builder().message("New phrase saved").build();

        doNothing().when(supportPhraseService).save(phrase);

        var responseDTO = supportController.addSupportPhrase(requestDTO);

        verify(supportPhraseService, times(1)).save(phrase);
        assertEquals(expectedResponse, responseDTO);
    }

    @Test
    void testGetSupportPhrase() {
        var phrase = "Test support phrase";
        var id = 1L;

        var expectedDTO = SupportPhraseDTO.builder().id(id).phrase(phrase).build();

        when(supportPhraseService.getRandomPhrase()).thenReturn(SupportPhrase.builder().id(id).message(phrase).build());

        var responseDTO = supportController.getSupportPhrase();

        verify(supportPhraseService, times(1)).getRandomPhrase();
        assertEquals(expectedDTO, responseDTO);
    }
}
