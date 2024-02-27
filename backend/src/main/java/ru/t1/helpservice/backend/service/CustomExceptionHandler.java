package ru.t1.helpservice.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.t1.helpservice.backend.dto.ErrorDto;
import ru.t1.helpservice.backend.exception.BaseHelpServiceException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({
            BaseHelpServiceException.class
    })
    public ResponseEntity<ErrorDto> handleCustomExceptions(Exception e) {
        return getResponseEntity(HttpStatus.BAD_REQUEST, prepareErrorResponse(e));
    }

    private ResponseEntity<ErrorDto> getResponseEntity(HttpStatus status, ErrorDto response) {
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    private ErrorDto prepareErrorResponse(Exception e) {
        return ErrorDto.builder().error(e.getMessage()).build();
    }
}
