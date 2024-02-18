package com.enigma.kingkost.handlerExeptions;

import com.enigma.kingkost.dto.response.HandleExeptionResponse;
import com.google.api.gax.rpc.AlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandleResponse {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public HandleExeptionResponse handleNotFoundExeption(NotFoundException notFoundException) {
        return HandleExeptionResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(notFoundException.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public HandleExeptionResponse handleExeptionResponseMissingParam(MissingServletRequestParameterException ex) {
        return HandleExeptionResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Required request parameter '" + ex.getParameterName())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public HandleExeptionResponse handleExeptionResponseNull(NullPointerException nullPointerException) {
        return HandleExeptionResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(nullPointerException.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public HandleExeptionResponse handleExeptionResponsee(Exception e) {
        return HandleExeptionResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public HandleExeptionResponse handleForbidden(AccessDeniedException e) {
        return HandleExeptionResponse.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyExistsException.class)
    public HandleExeptionResponse handleExeptionResponse(AlreadyExistsException e) {
        return HandleExeptionResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
    }
}
