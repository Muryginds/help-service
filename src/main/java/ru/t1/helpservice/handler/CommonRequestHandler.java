package ru.t1.helpservice.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import ru.t1.helpservice.utils.JsonUtils;
import ru.t1.helpservice.utils.ResponseUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@AllArgsConstructor
public class CommonRequestHandler implements RequestHandler {
    private final Method method;
    private final Object object;

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            var params = method.getParameters();
            if (params.length == 0) {
                var responseDTO = method.invoke(object);
                var output = JsonUtils.writeJsonAsBytes(responseDTO);
                ResponseUtils.callOkResponse(response, output);
            } else {
                var paramClass = params[0].getType();
                var requestDTO = JsonUtils.readJson(request.getReader(), paramClass);
                var eventDTO = method.invoke(object, requestDTO);
                var output = JsonUtils.writeJsonAsBytes(eventDTO);
                ResponseUtils.callOkResponse(response, output);
            }
        } catch (InvocationTargetException ex) {
            ResponseUtils.callErrorResponse(response, ex.getCause());
        } catch (IllegalAccessException | IOException e) {
            ResponseUtils.callErrorResponse(response, e);
        }
    }
}
