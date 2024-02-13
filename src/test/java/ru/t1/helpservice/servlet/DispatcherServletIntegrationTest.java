package ru.t1.helpservice.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.t1.helpservice.dto.BaseResponseDTO;
import ru.t1.helpservice.dto.SupportPhraseRequestDTO;
import ru.t1.helpservice.utils.JsonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DispatcherServletIntegrationTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletInputStream inputStream;
    @Mock
    private ServletOutputStream outputStream;
    private DispatcherServlet dispatcherServlet;

    @BeforeEach
    void setup() throws ServletException {
        MockitoAnnotations.openMocks(this);
        dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.init();
    }

    @Test
    void testDoGet_whenPhraseExists_thenReturnStatusOk() throws IOException, ServletException {
        when(request.getInputStream()).thenReturn(inputStream);
        when(request.getPathInfo()).thenReturn("/api/v1/support");
        when(response.getOutputStream()).thenReturn(outputStream);

        dispatcherServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).setContentType("application/json");
        verify(response).getOutputStream();
    }

    @Test
    void testDoPost_whenDataCorrect_thenReturnStatusOk() throws IOException, ServletException {
        var requestDTO = new SupportPhraseRequestDTO("Support phrase");
        var responseDTO = new BaseResponseDTO("New phrase saved");
        byte[] requestBody = JsonUtils.writeJsonAsBytes(requestDTO);
        byte[] responseBody = JsonUtils.writeJsonAsBytes(responseDTO);
        var reader = new BufferedReader(new StringReader(new String(requestBody)));
        when(request.getReader()).thenReturn(reader);
        when(request.getInputStream()).thenReturn(inputStream);
        when(request.getPathInfo()).thenReturn("/api/v1/support");
        when(response.getOutputStream()).thenReturn(outputStream);

        dispatcherServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).setContentType("application/json");
        verify(response).getOutputStream();
        verify(outputStream).write(responseBody);
        verify(response.getOutputStream()).write(responseBody);
    }
}