package ru.t1.helpservice.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.t1.helpservice.entity.SupportPhrase;
import ru.t1.helpservice.exception.BaseHelpServiceException;
import ru.t1.helpservice.service.SupportPhraseService;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SupportServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private SupportPhraseService supportPhraseService;

    private SupportServlet supportServlet;

    private StringWriter stringWriter;

    @BeforeEach
    void setUp() throws ServletException, IOException {
        MockitoAnnotations.openMocks(this);
        supportServlet = new SupportServlet();
        supportServlet.setSupportPhraseService(supportPhraseService);
        stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    void testDoGet_Success() throws Exception {
        var supportPhrase = SupportPhrase.builder().message("Test support phrase").build();
        when(supportPhraseService.getRandomPhrase()).thenReturn(supportPhrase);

        supportServlet.doGet(request, response);

        verify(response).setContentType("text/plain");

        assertTrue(stringWriter.toString().contains("Test support phrase"));
    }

    @Test
    void testDoGet_Exception() throws Exception {
        when(supportPhraseService.getRandomPhrase()).thenThrow(new BaseHelpServiceException("Error"));

        supportServlet.doGet(request, response);

        verify(response).setContentType("text/plain");

        assertTrue(stringWriter.toString().contains("Error: Error"));
    }

    @Test
    void testDoPost_Success() throws Exception {
        when(request.getParameter("phrase")).thenReturn("Test support phrase");

        supportServlet.doPost(request, response);

        verify(response).setContentType("text/plain");

        assertTrue(stringWriter.toString().contains("New phrase saved"));
    }

    @Test
    void testDoPost_Exception() throws Exception {
        when(request.getParameter("phrase")).thenReturn("");

        doThrow(new BaseHelpServiceException("Error")).when(supportPhraseService).save(anyString());

        supportServlet.doPost(request, response);

        verify(response).setContentType("text/plain");

        assertTrue(stringWriter.toString().contains("Error: Error"));
    }
}
