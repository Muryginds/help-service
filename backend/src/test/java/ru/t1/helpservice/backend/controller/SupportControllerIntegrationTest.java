package ru.t1.helpservice.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.t1.helpservice.backend.dto.SupportPhraseRequestDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SupportControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addSupportPhraseIntegrationTest() throws Exception {
        SupportPhraseRequestDTO requestDTO = new SupportPhraseRequestDTO("Test phrase");

        mockMvc.perform(post("/api/v1/support")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("New phrase saved"));
    }

    @Test
    void getSupportPhraseIntegrationTest() throws Exception {
        mockMvc.perform(get("/api/v1/support"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.phraseId").value(1))
                .andExpect(jsonPath("$.phraseText").value("Default support phrase"));
    }
}
