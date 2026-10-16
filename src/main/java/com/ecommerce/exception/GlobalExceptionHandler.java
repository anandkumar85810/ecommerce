package com.ecommerce.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach((error ->
                errors.put(error.getField(), error.getDefaultMessage())));

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("status", 400);
        response.put("error", "Validation Failed");
        response.put("message", "Please correct the following information");
        response.put("fieldErrors", errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", 404);
        response.put("error", "Resource Not Found");
        response.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }
}
