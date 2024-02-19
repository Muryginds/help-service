package ru.t1.helpservice.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.t1.helpservice.backend.entity.SupportPhrase;
import ru.t1.helpservice.backend.exception.SupportPhraseException;
import ru.t1.helpservice.backend.exception.SupportPhraseNotFoundException;
import ru.t1.helpservice.backend.repository.SupportPhraseRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupportPhraseServiceTest {

    @Mock
    private SupportPhraseRepository supportPhraseRepository;

    @InjectMocks
    private SupportPhraseService supportPhraseService;

    @Test
    void testSave_InputOk_Success() {
        var phrase = "This is a test phrase";

        supportPhraseService.save(phrase);

        verify(supportPhraseRepository, times(1)).save(any(SupportPhrase.class));
    }

    @Test
    void testSave_EmptyPhrase_ExceptionThrown() {
        var phrase = "";

        assertThrows(SupportPhraseException.class, () -> supportPhraseService.save(phrase));
        verifyNoInteractions(supportPhraseRepository);
    }

    @Test
    void testSave_NullPhrase_ExceptionThrown() {
        String phrase = null;

        assertThrows(SupportPhraseException.class, () -> supportPhraseService.save(phrase));
        verifyNoInteractions(supportPhraseRepository);
    }

    @Test
    void testGetRandomPhrase_InputOk_Success() {
        var message = "This is a random phrase";
        var supportPhrase = SupportPhrase.builder().message(message).build();
        when(supportPhraseRepository.getRandomPhrase()).thenReturn(Optional.of(supportPhrase));

        SupportPhrase retrievedPhrase = supportPhraseService.getRandomPhrase();

        assertEquals(message, retrievedPhrase.getMessage());
        verify(supportPhraseRepository, times(1)).getRandomPhrase();
    }

    @Test
    void testGetRandomPhrase_NotFound_ExceptionThrown() {
        when(supportPhraseRepository.getRandomPhrase()).thenReturn(Optional.empty());

        assertThrows(SupportPhraseNotFoundException.class, () -> supportPhraseService.getRandomPhrase());
        verify(supportPhraseRepository, times(1)).getRandomPhrase();
    }
}
