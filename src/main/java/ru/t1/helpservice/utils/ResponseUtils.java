package ru.t1.helpservice.utils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import ru.t1.helpservice.dto.ErrorDTO;

import java.io.IOException;

@UtilityClass
public class ResponseUtils {
    public void callOkResponse(
            HttpServletResponse response,
            byte[] output
    ) throws IOException {
        prepareResponse(response, HttpServletResponse.SC_OK, output);
    }

    public void callErrorResponse(
            HttpServletResponse response,
            Throwable throwable
    ) throws IOException {
        var errorDTO = ErrorDTO.builder().error(throwable.getMessage()).build();
        var output = JsonUtils.writeJsonAsBytes(errorDTO);
        prepareResponse(response, HttpServletResponse.SC_BAD_REQUEST, output);
    }

    private void prepareResponse(
            HttpServletResponse response,
            Integer statusCode,
            byte[] output
    ) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getOutputStream().write(output);
    }
}
