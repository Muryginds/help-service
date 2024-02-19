package ru.t1.helpservice.broker.runner;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConsumingMethodRunner {
    private final Executor executor = Executors.newSingleThreadExecutor();

    public void run(Runnable runnable) {
        executor.execute(runnable);
    }
}
