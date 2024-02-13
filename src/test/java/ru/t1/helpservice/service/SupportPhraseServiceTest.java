package ru.t1.helpservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.t1.helpservice.entity.SupportPhrase;
import ru.t1.helpservice.exception.SupportPhraseException;
import ru.t1.helpservice.exception.SupportPhraseNotFoundException;
import ru.t1.helpservice.repository.SupportPhraseRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SupportPhraseServiceTest {

    @Mock
    private SupportPhraseRepository supportPhraseRepository;

    private SupportPhraseService supportPhraseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        supportPhraseService = new SupportPhraseService();
        supportPhraseService.setSupportPhraseRepository(supportPhraseRepository);
    }

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
