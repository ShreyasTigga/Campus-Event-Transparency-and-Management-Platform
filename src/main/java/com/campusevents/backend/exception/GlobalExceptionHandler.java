package com.campusevents.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 - Bad Request
    @ExceptionHandler({
            EmailAlreadyExistsException.class,
            InvalidCredentialsException.class
    })
    public ResponseEntity<Object> handleBadRequest(RuntimeException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), request.getRequestURI(), HttpStatus.BAD_REQUEST);
    }

    // 403 - Forbidden
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleForbidden(AccessDeniedException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), request.getRequestURI(), HttpStatus.FORBIDDEN);
    }

    // 404 - Not Found
    @ExceptionHandler({
            UserNotFoundException.class,
            ResourceNotFoundException.class
    })
    public ResponseEntity<Object> handleNotFound(RuntimeException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND);
    }

    // 500 - Internal Server Error (ONLY ONE GENERIC HANDLER)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternal(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(
                "Something went wrong. Please contact support.",
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // 🔧 Central response builder
    private ResponseEntity<Object> buildErrorResponse(String message, String path, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", path);

        return ResponseEntity.status(status).body(body);
    }
}
