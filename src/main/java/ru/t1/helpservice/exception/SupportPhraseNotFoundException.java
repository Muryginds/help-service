package ru.t1.helpservice.exception;

public class SupportPhraseNotFoundException extends BaseHelpServiceException {

    public SupportPhraseNotFoundException() {
        super("No support phrase found");
    }
}
