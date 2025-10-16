package com.dauphine.event_management_backend.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email) {
        super("Un utilisateur avec l'email " + email + " existe déjà.");
    }
    
    public UserAlreadyExistsException(String field, String value) {
        super("Un utilisateur avec " + field + " '" + value + "' existe déjà.");
    }
}

