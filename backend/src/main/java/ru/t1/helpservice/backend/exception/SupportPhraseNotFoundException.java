package ru.t1.helpservice.backend.exception;

public class SupportPhraseNotFoundException extends BaseHelpServiceException {

    public SupportPhraseNotFoundException() {
        super("No support phrase found");
    }
}
