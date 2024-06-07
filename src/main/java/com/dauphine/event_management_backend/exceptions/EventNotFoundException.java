package com.dauphine.event_management_backend.exceptions;

import java.util.UUID;

public class EventNotFoundException extends Exception{
    public EventNotFoundException(UUID uuid) {
        super("Event with id : "+ uuid + " not found.");
    }
}
