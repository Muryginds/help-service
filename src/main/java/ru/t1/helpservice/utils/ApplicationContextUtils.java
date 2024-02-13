package ru.t1.helpservice.utils;

import lombok.experimental.UtilityClass;
import ru.t1.helpservice.context.ApplicationContext;

@UtilityClass
public class ApplicationContextUtils {
    private final ApplicationContext applicationContext = new ApplicationContext();

    public ApplicationContext getContext() {
        return applicationContext;
    }

}
