package ru.t1.helpservice.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import ru.t1.helpservice.entity.SupportPhrase;
import ru.t1.helpservice.exception.BaseHelpServiceException;
import ru.t1.helpservice.service.SupportPhraseService;
import ru.t1.helpservice.utils.ApplicationContextUtils;

import java.io.IOException;
import java.io.PrintWriter;

public class SupportServlet extends HttpServlet {
    @Setter
    private SupportPhraseService supportPhraseService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");

        var printWriter = response.getWriter();
        try {
            SupportPhrase phrase = supportPhraseService.getRandomPhrase();
            printWriter.write(phrase.getMessage());
        } catch (BaseHelpServiceException ex) {
            handleException(printWriter, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");

        var printWriter = response.getWriter();
        try {
            String newPhrase = request.getParameter("phrase");
            supportPhraseService.save(newPhrase);
            printWriter.write("New phrase saved");
        } catch (BaseHelpServiceException ex) {
            handleException(printWriter, ex);
        }
    }

    private void handleException(PrintWriter printWriter, BaseHelpServiceException ex) {
        printWriter.println("Error: " + ex.getMessage());
    }

    @Override
    public void init() throws ServletException {
        supportPhraseService = ApplicationContextUtils.getContext().getInstance(SupportPhraseService.class);
    }
}
