package ru.t1.helpservice.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.t1.helpservice.annotation.Loggable;

import java.io.IOException;

public interface RequestHandler {
    @Loggable
    void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
