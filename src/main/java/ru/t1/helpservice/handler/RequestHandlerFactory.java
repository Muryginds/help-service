package ru.t1.helpservice.handler;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RequestHandlerFactory {

    public RequestHandler getHandler(Method method, Object object) {
        return (RequestHandler) wrapWithLoggingProxy(new CommonRequestHandler(method, object));
    }

    private Object wrapWithLoggingProxy(Object object) {
        return Proxy.newProxyInstance(
                object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                new LoggingInvocationHandler(object)
        );
    }
}
