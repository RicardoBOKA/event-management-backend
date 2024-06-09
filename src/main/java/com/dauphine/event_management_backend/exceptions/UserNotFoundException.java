package com.dauphine.event_management_backend.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID uuid) {
        super("User : "+ uuid + " not found.");
    }
}
