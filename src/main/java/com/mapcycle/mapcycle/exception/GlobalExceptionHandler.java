package com.mapcycle.mapcycle.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errorsMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorsMap
                    .computeIfAbsent(error.getField(), key -> new ArrayList<>())
                    .add(error.getDefaultMessage());
        });

        ApiError apiError = new ApiError(
                UUID.randomUUID().toString(),
                new Date(),
                errorsMap
        );

        return ResponseEntity.badRequest().body(apiError);
    }
}
