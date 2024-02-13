package ru.t1.helpservice.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import ru.t1.helpservice.handler.GetRequestHandlerProvider;
import ru.t1.helpservice.handler.PostRequestHandlerProvider;
import ru.t1.helpservice.utils.ApplicationContextUtils;

import java.io.IOException;

@WebServlet("/*")
@Setter
public class DispatcherServlet extends HttpServlet {
    private GetRequestHandlerProvider getRequestHandlerProvider;
    private PostRequestHandlerProvider postRequestHandlerProvider;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var path = req.getPathInfo();
        var getRequestHandler = getRequestHandlerProvider.getHandler(path);
        getRequestHandler.handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var path = req.getPathInfo();
        var postRequestHandler = postRequestHandlerProvider.getHandler(path);
        postRequestHandler.handleRequest(req, resp);
    }

    @Override
    public void init() throws ServletException {
        getRequestHandlerProvider = ApplicationContextUtils.getContext()
                .getInstance(GetRequestHandlerProvider.class);
        postRequestHandlerProvider = ApplicationContextUtils.getContext()
                .getInstance(PostRequestHandlerProvider.class);
        getRequestHandlerProvider.init();
        postRequestHandlerProvider.init();
    }
}
