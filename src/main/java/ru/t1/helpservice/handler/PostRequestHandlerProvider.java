package ru.t1.helpservice.handler;

import org.reflections.Reflections;
import ru.t1.helpservice.annotation.Autowired;
import ru.t1.helpservice.annotation.Controller;
import ru.t1.helpservice.annotation.PostMapping;
import ru.t1.helpservice.exception.BaseHelpServiceException;
import ru.t1.helpservice.utils.ApplicationContextUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PostRequestHandlerProvider {
    private final Map<String, RequestHandler> pathForHandler = new HashMap<>();
    private RequestHandlerFactory requestHandlerFactory;

    @Autowired
    public void setRequestHandlerFactory(RequestHandlerFactory requestHandlerFactory) {
        this.requestHandlerFactory = requestHandlerFactory;
    }

    public void init() {
        var applicationContext = ApplicationContextUtils.getContext();
        var reflections = new Reflections("ru.t1.helpservice.controller");
        var controllers = reflections.getTypesAnnotatedWith(Controller.class)
                .stream()
                .map(applicationContext::getInstance)
                .toList();

        for (var controller : controllers) {
            var methods = Arrays.stream(controller.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(PostMapping.class))
                    .toList();
            for (var method : methods) {
                var path = method.getAnnotation(PostMapping.class).path();
                pathForHandler.put(path, requestHandlerFactory.getHandler(method, controller));
            }
        }
    }

    public RequestHandler getHandler(String path) {
        return Optional.ofNullable(pathForHandler.get(path))
                .orElseThrow(() -> new BaseHelpServiceException("No POST handler found"));
    }
}
