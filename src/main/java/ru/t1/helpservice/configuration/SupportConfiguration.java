package ru.t1.helpservice.configuration;

import ru.t1.helpservice.annotation.Configuration;
import ru.t1.helpservice.annotation.Instance;
import ru.t1.helpservice.controller.SupportController;
import ru.t1.helpservice.repository.SupportPhraseRepository;
import ru.t1.helpservice.repository.SupportPhraseRepositoryImpl;
import ru.t1.helpservice.service.SupportPhraseService;

@Configuration
public class SupportConfiguration {

    @Instance
    public SupportPhraseRepository supportPhraseRepository() {
        return new SupportPhraseRepositoryImpl();
    }

    @Instance
    public SupportPhraseService supportPhraseService() {
        return new SupportPhraseService();
    }

    @Instance
    public SupportController supportController() {
        return new SupportController();
    }
}
