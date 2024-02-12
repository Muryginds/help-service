package ru.t1.helpservice.context;

import org.junit.jupiter.api.Test;
import ru.t1.helpservice.service.SupportPhraseService;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationContextTest {

    @Test
    void testApplicationContext_shouldReturnInstanceByClass() {
        var applicationContext = new ApplicationContext();
        assertEquals(
                SupportPhraseService.class,
                applicationContext.getInstance(SupportPhraseService.class).getClass()
        );
    }
}