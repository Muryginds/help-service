package ru.t1.helpservice.servlet;

import lombok.AllArgsConstructor;
import ru.t1.helpservice.entity.SupportPhrase;
import ru.t1.helpservice.exception.BaseHelpServiceException;
import ru.t1.helpservice.repository.SupportPhraseRepositoryImpl;
import ru.t1.helpservice.service.SupportPhraseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@AllArgsConstructor
public class SupportServlet extends HttpServlet {
    private SupportPhraseService supportPhraseService;

    public SupportServlet() {
        supportPhraseService = new SupportPhraseService(new SupportPhraseRepositoryImpl());
    }

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
}
