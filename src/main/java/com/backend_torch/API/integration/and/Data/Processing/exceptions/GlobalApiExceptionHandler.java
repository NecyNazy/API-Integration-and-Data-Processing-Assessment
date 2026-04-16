package com.backend_torch.API.integration.and.Data.Processing.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalApiExceptionHandler {

    private ResponseEntity<Map<String, Object>> build(String status, String message, int httpStatus) {
        return ResponseEntity.status(httpStatus).body(Map.of(
                "Status", status,
                "Message", message
        ));
    }

    // Your custom exceptions
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handle(ApiException ex) {
        return build(ex.getStatus(), ex.getMessage(), ex.getHttpStatus());
    }

    // Validation errors: @Valid on @RequestBody DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getAllErrors().isEmpty()
                ? "Validation failed"
                : ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return build("422", msg, HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    // Validation errors: @Valid on @ModelAttribute / form-data, etc.
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBind(BindException ex) {
        String msg = ex.getAllErrors().isEmpty()
                ? "Validation failed"
                : ex.getAllErrors().get(0).getDefaultMessage();
        return build("422", msg, HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    // Bad JSON / wrong field types / malformed JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleBadJson(HttpMessageNotReadableException ex) {
        return build("400", "Invalid request body (malformed JSON or wrong field types)", HttpStatus.BAD_REQUEST.value());
    }

    // Wrong HTTP method (GET instead of POST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        return build("401", "Method not allowed for this endpoint", HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    // 404 for “No static resource ...” (your log)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoResource(NoResourceFoundException ex, HttpServletRequest req) {
        return build("404", "Endpoint not found: " + req.getRequestURI(), HttpStatus.NOT_FOUND.value());
    }
    //Handle missing query params e.g /api/classify (no ?name)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissingParams(
            MissingServletRequestParameterException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("Status", "error");
        response.put("Message", ex.getParameterName() + " is required and must be provided");

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }


    // Catch-all (always keep this last)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAny(Exception ex) {
        // Log ex properly with your logger here
        return build("96", "System error", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}