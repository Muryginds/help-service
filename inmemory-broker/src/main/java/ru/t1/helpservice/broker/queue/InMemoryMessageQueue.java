package ru.t1.helpservice.broker.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InMemoryMessageQueue implements MessageQueue {
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    @Override
    public void publish(String message) {
       var result = queue.offer(message);
       if (!result) {
           System.out.printf("Message not added '%s'%n", message);
       }
    }

    @Override
    public String poll() {
        return queue.poll();
    }
}
