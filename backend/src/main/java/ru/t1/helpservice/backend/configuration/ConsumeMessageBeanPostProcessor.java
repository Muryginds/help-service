package ru.t1.helpservice.backend.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.t1.helpservice.backend.annotation.Subscribe;
import ru.t1.helpservice.backend.exception.ConsumingMethodRunnerException;
import ru.t1.helpservice.broker.runner.ConsumingMethodRunner;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ConsumeMessageBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, Object> annotatedBeans = new HashMap<>();
    private final ConsumingMethodRunner consumingMethodRunner;

    @Override
    public Object postProcessBeforeInitialization(Object bean, @NonNull String beanName) throws BeansException {
        var methods = bean.getClass().getMethods();
        var annotatedMethod = Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(Subscribe.class))
                .findFirst();
        annotatedMethod.ifPresent(m -> annotatedBeans.put(beanName, bean));
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean,@NonNull String beanName) throws BeansException {
        var objectOptional = Optional.ofNullable(annotatedBeans.get(beanName));
        objectOptional.ifPresent(object -> {
            if (object instanceof Runnable runnable) {
                consumingMethodRunner.run(runnable);
            } else {
                throw new ConsumingMethodRunnerException(
                        "Bean '%s' annotated with @Subscribe must implement Runnable".formatted(beanName)
                );
            }
        });
        return bean;
    }
}
