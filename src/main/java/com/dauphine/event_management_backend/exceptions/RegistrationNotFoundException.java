package com.dauphine.event_management_backend.exceptions;

import java.util.UUID;

public class RegistrationNotFoundException extends Exception {
    public RegistrationNotFoundException(UUID uuid) {
        super("Registration : "+ uuid + " not found.");
    }

}
