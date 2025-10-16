package com.dauphine.event_management_backend.exceptions;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler({
            EventNotFoundException.class,
            UserNotFoundException.class,
            RegistrationNotFoundException.class
    })
    public ResponseEntity<String> handleNotFounException(Exception ex) {
        logger.warn("[NOT FOUND] {}", ex.getMessage());
        return ResponseEntity
                .status(404)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        logger.warn("[CONFLICT] {}", ex.getMessage());
        return ResponseEntity
                .status(409)
                .body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequestException(IllegalArgumentException ex) {
        logger.warn("[BAD REQUEST] {}", ex.getMessage());
        return ResponseEntity
                .status(400)
                .body(ex.getMessage());
    }
}
