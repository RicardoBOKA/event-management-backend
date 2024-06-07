package com.dauphine.event_management_backend.services;

import com.dauphine.event_management_backend.exceptions.EventNotFoundException;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.Event;
import com.dauphine.event_management_backend.ressources.Location;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventService {
    Event createEvent(Event event) throws UserNotFoundException;

    // Update an existing event
    Event updateEvent(UUID eventId, Event event) throws EventNotFoundException;

    // Delete an event by its ID
    void deleteEvent(UUID eventId);

    // Retrieve an event by its ID
    Event findEventById(UUID eventId) throws EventNotFoundException;

    // List all events
    List<Event> findAllEvents();

    // List events by organizer
    List<Event> findEventsByOrganizer(UUID organizerId) throws UserNotFoundException;
    // Search events based on different criteria such as date, location, name, etc.
    List<Event> searchEvents(LocalDateTime startDate, LocalDateTime endDate, String name, String location);
}
