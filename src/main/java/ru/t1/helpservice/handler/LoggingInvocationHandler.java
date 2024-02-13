package ru.t1.helpservice.handler;

import ru.t1.helpservice.annotation.Loggable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggingInvocationHandler implements InvocationHandler {
    private final Object target;

    public LoggingInvocationHandler(Object object) {
        target = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Loggable.class)) {
            System.out.println("Method START");
            System.out.printf("Method called: '%s'%n", method.getName());
            var result = method.invoke(target, args);
            System.out.println("Method STOP");
            return result;
        }
        return method.invoke(target, args);
    }
}
