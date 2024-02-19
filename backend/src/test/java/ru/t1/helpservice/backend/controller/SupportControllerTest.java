package ru.t1.helpservice.backend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.t1.helpservice.backend.dto.BaseResponseDTO;
import ru.t1.helpservice.backend.dto.SupportPhraseDTO;
import ru.t1.helpservice.backend.dto.SupportPhraseRequestDTO;
import ru.t1.helpservice.backend.entity.SupportPhrase;
import ru.t1.helpservice.backend.service.SupportPhraseService;
import ru.t1.helpservice.broker.publisher.Publisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupportControllerTest {

    @Mock
    private SupportPhraseService supportPhraseService;
    @Mock
    private Publisher publisher;

    @InjectMocks
    private SupportController supportController;

    @Test
    void testAddSupportPhrase() {
        var phrase = "Test support phrase";
        var requestDTO = new SupportPhraseRequestDTO(phrase);

        var expectedResponse = BaseResponseDTO.builder().message("New phrase saved").build();

        doNothing().when(publisher).publishMessage(phrase);

        var responseDTO = supportController.addSupportPhrase(requestDTO);

        verify(publisher, times(1)).publishMessage(phrase);
        assertEquals(expectedResponse, responseDTO);
    }

    @Test
    void testGetSupportPhrase() {
        var phrase = "Test support phrase";
        var id = 1L;

        var supportPhrase = SupportPhrase.builder().id(id).message(phrase).build();
        var expectedDTO = SupportPhraseDTO.builder().id(id).phrase(phrase).build();

        when(supportPhraseService.getRandomPhrase()).thenReturn(supportPhrase);

        var responseDTO = supportController.getSupportPhrase();

        verify(supportPhraseService, times(1)).getRandomPhrase();
        assertEquals(expectedDTO, responseDTO);
    }
}
