package com.dauphine.event_management_backend.services;

import com.dauphine.event_management_backend.exceptions.EventNotFoundException;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.Event;
import com.dauphine.event_management_backend.ressources.Location;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
/**
 * Service interface for managing events.
 */
public interface EventService {
    /**
     * Creates a new event.
     *
     * @param eventName   the name of the event
     * @param createdDate the creation date of the event
     * @param startEvent  the start date and time of the event
     * @param endEvent    the end date and time of the event
     * @param location    the location of the event
     * @param description the description of the event
     * @param organizerId the ID of the organizer
     * @return the created event
     * @throws UserNotFoundException if the user (organizer) is not found
     * @throws EventNotFoundException if the event could not be created
     */
    Event createEvent(String eventName, LocalDateTime createdDate, LocalDateTime startEvent,
                      LocalDateTime endEvent, Location location, String description, UUID organizerId) throws UserNotFoundException, EventNotFoundException;

    /**
     * Updates an existing event.
     *
     * @param eventId      the ID of the event to update
     * @param eventName    the new name of the event
     * @param createdDate  the new creation date of the event
     * @param startEvent   the new start date and time of the event
     * @param endEvent     the new end date and time of the event
     * @param location     the new location of the event
     * @param description  the new description of the event
     * @param organizerId  the ID of the organizer
     * @return the updated event
     * @throws EventNotFoundException if the event is not found
     * @throws UserNotFoundException if the user (organizer) is not found
     */
    Event updateEvent(UUID eventId, String eventName, LocalDateTime createdDate, LocalDateTime startEvent,
                      LocalDateTime endEvent, Location location, String description, UUID organizerId) throws EventNotFoundException, UserNotFoundException;

    /**
     * Deletes an event by its ID.
     *
     * @param eventId the ID of the event to delete
     * @throws EventNotFoundException if the event is not found
     */
    void deleteEvent(UUID eventId) throws EventNotFoundException;

    /**
     * Retrieves an event by its ID.
     *
     * @param eventId the ID of the event to retrieve
     * @return the found event
     * @throws EventNotFoundException if the event is not found
     */
    Event findEventById(UUID eventId) throws EventNotFoundException;

    /**
     * Lists all events.
     *
     * @return the list of all events
     */
    List<Event> findAllEvents();

    /**
     * Lists events by organizer.
     *
     * @param organizerId the ID of the organizer
     * @return the list of events organized by the given organizer
     * @throws UserNotFoundException if the user (organizer) is not found
     */
    List<Event> findEventsByOrganizer(UUID organizerId) throws UserNotFoundException;

    /**
     * Searches events based on different criteria such as date, location, name, etc.
     *
     * @param startDate the start date for the search
     * @param endDate   the end date for the search
     * @return the list of events matching the search criteria
     */
    List<Event> searchEvents(LocalDateTime startDate, LocalDateTime endDate);
    /**
     * Finds all events with names similar to the given name.
     *
     * @param eventName the name to search for
     * @return the list of events with similar names
     */
    List<Event> findAllLikeNames(String eventName);
}
