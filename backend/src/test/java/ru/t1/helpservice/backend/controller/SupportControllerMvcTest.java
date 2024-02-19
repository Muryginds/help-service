package ru.t1.helpservice.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.t1.helpservice.backend.dto.SupportPhraseRequestDTO;
import ru.t1.helpservice.backend.entity.SupportPhrase;
import ru.t1.helpservice.backend.service.SupportPhraseService;
import ru.t1.helpservice.broker.publisher.Publisher;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SupportController.class)
class SupportControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupportPhraseService supportPhraseService;

    @MockBean
    private Publisher publisher;

    @Test
    void addSupportPhraseTest() throws Exception {
        var requestDTO = new SupportPhraseRequestDTO("Test phrase");

        mockMvc.perform(post("/api/v1/support")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("New phrase saved"));

        verify(publisher).publishMessage("Test phrase");
    }

    @Test
    void getSupportPhraseTest() throws Exception {
        var supportPhrase = SupportPhrase.builder().id(1L).message("Test phrase").build();

        when(supportPhraseService.getRandomPhrase()).thenReturn(supportPhrase);

        mockMvc.perform(get("/api/v1/support"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.phraseId").value(1))
                .andExpect(jsonPath("$.phraseText").value("Test phrase"));
    }
}
