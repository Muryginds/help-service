package ru.t1.helpservice.backend.service;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.t1.helpservice.backend.dto.ErrorResponseDto;
import ru.t1.helpservice.backend.exception.BaseHelpServiceException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({
            BaseHelpServiceException.class,
            ValidationException.class
    })
    public ResponseEntity<ErrorResponseDto> handleCustomExceptions(Exception e) {
        return getResponseEntity(HttpStatus.BAD_REQUEST, prepareErrorResponse(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleArgumentNotValidationException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(String.format("%s - %s", fieldName, errorMessage));
        });
        return getResponseEntity(HttpStatus.BAD_REQUEST, responseWithError(errors));
    }

    private ResponseEntity<ErrorResponseDto> getResponseEntity(HttpStatus status, ErrorResponseDto response) {
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    private ErrorResponseDto prepareErrorResponse(Exception e) {
        return responseWithError(List.of(e.getLocalizedMessage()));
    }

    private ErrorResponseDto responseWithError(List<String> errors) {
        return ErrorResponseDto.builder().errors(errors).build();
    }
}
