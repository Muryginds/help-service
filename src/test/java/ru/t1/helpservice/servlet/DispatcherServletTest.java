package ru.t1.helpservice.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.t1.helpservice.handler.GetRequestHandlerProvider;
import ru.t1.helpservice.handler.PostRequestHandlerProvider;
import ru.t1.helpservice.handler.RequestHandler;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DispatcherServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private GetRequestHandlerProvider getRequestHandlerProvider;

    @Mock
    private PostRequestHandlerProvider postRequestHandlerProvider;

    @Mock
    private RequestHandler getRequestHandler;

    @Mock
    private RequestHandler postRequestHandler;

    private DispatcherServlet dispatcherServlet;

    @BeforeEach
    void setUp() throws ServletException, IOException {
        MockitoAnnotations.openMocks(this);
        dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setGetRequestHandlerProvider(getRequestHandlerProvider);
        dispatcherServlet.setPostRequestHandlerProvider(postRequestHandlerProvider);
    }

    @Test
    void testDoGet() throws Exception {
        when(request.getPathInfo()).thenReturn("/some-path");
        when(getRequestHandlerProvider.getHandler("/some-path")).thenReturn(getRequestHandler);

        dispatcherServlet.doGet(request, response);

        verify(getRequestHandlerProvider).getHandler("/some-path");
        verify(getRequestHandler).handleRequest(request, response);
    }

    @Test
    void testDoPost() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/another-path");
        when(postRequestHandlerProvider.getHandler("/another-path")).thenReturn(postRequestHandler);

        dispatcherServlet.doPost(request, response);

        verify(postRequestHandlerProvider).getHandler("/another-path");
        verify(postRequestHandler).handleRequest(request, response);
    }
}
