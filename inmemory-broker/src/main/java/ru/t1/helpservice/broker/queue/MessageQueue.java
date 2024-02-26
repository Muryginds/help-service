package ru.t1.helpservice.broker.queue;

public interface MessageQueue {

    void publish(String message);

    String poll();
}
