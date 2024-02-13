package ru.t1.helpservice.context;

import org.reflections.Reflections;
import ru.t1.helpservice.annotation.Autowired;
import ru.t1.helpservice.annotation.Configuration;
import ru.t1.helpservice.annotation.Instance;
import ru.t1.helpservice.exception.ApplicationContextException;
import ru.t1.helpservice.exception.BaseHelpServiceException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ApplicationContext {
    private final Map<Class<?>, Object> instances = new HashMap<>();

    public ApplicationContext() {
        var reflections = new Reflections("ru.t1.helpservice.configuration");
        var configurations = reflections.getTypesAnnotatedWith(Configuration.class)
                .stream()
                .map(this::getNewInstance)
                .toList();
        for (var configuration : configurations) {
            var methods = Arrays.stream(configuration.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(Instance.class))
                    .toList();
            for (var method : methods) {
                instances.put(method.getReturnType(), invokeMethod(method, configuration));
            }
        }
        for (var object : instances.values()) {
            var setters = Arrays.stream(object.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(Autowired.class))
                    .toList();
            for (var method : setters) {
                var args = Arrays.stream(method.getParameters())
                        .map(parameter -> instances.get(parameter.getType()))
                        .toArray();
                invokeMethod(method, object, args);
            }
        }
    }

    private Object invokeMethod(Method method, Object object) {
        try {
            return method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ApplicationContextException(e.getMessage());
        }
    }

    private void invokeMethod(Method method, Object object, Object[] args) {
        try {
            method.invoke(object, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ApplicationContextException(e.getMessage());
        }
    }

    private Object getNewInstance(Class<?> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException e) {
            throw new ApplicationContextException(e.getMessage());
        }
    }

    public <T> T getInstance(Class<T> type) {
        return (T) Optional.ofNullable(instances.get(type))
                .orElseThrow(() -> new BaseHelpServiceException("No instance found"));
    }
}
