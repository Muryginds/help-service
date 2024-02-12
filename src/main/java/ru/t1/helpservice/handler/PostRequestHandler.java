package ru.t1.helpservice.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface PostRequestHandler {

    void handleRequest(HttpServletRequest request, HttpServletResponse response);
}
