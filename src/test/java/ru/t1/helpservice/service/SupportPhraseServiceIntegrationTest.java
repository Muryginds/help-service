package ru.t1.helpservice.service;

import org.junit.jupiter.api.Test;
import ru.t1.helpservice.utils.ApplicationContextUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SupportPhraseServiceIntegrationTest {
    @Test
    void getRandomPhrase() {
        var applicationContext = ApplicationContextUtils.getContext();
        var supportService = applicationContext.getInstance(SupportPhraseService.class);
        var newSupportPhrase = "New support phrase";
        supportService.save(newSupportPhrase);
        assertNotNull(supportService);
        var actualPhrase = supportService.getRandomPhrase();
        assertNotNull(actualPhrase.getMessage());
    }
}