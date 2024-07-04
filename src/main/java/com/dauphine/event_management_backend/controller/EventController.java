package com.dauphine.event_management_backend.controller;


import com.dauphine.event_management_backend.dto.EventRequest;
import com.dauphine.event_management_backend.exceptions.EventNotFoundException;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.Event;
import com.dauphine.event_management_backend.services.EventService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
/**
 * Controller for handling requests related to events.
 */
@RestController
@RequestMapping("/v1/events")
public class EventController {

    private final EventService eventService;
    /**
     * Constructor to initialize EventService.
     *
     * @param eventService the event service to be used.
     */
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    /**
     * Retrieve all events.
     *
     * @return a list of all events.
     */
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.findAllEvents();
        return ResponseEntity.ok(events);
    }
    /**
     * Retrieve an event by its ID.
     *
     * @param eventId the UUID of the event to be retrieved.
     * @return the event with the specified ID.
     * @throws EventNotFoundException if the event is not found.
     */
    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable UUID eventId) throws EventNotFoundException {
            Event event = eventService.findEventById(eventId);
            return ResponseEntity.ok(event);

    }
    /**
     * Create a new event.
     *
     * @param eventRequest the event request containing event details.
     * @return the created event.
     */
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventRequest eventRequest) {
        try {
            Event createdEvent = eventService.createEvent(
                    eventRequest.getEventName(),
                    eventRequest.getCreatedDate(),
                    eventRequest.getStartEvent(),
                    eventRequest.getEndEvent(),
                    eventRequest.getLocation(),
                    eventRequest.getDescription(),
                    eventRequest.getOrganizerId()
            );
            return ResponseEntity
                    .created(URI.create("/v1/events/" + createdEvent.getEventId()))
                    .body(createdEvent);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (EventNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Update an existing event.
     *
     * @param eventId the UUID of the event to be updated.
     * @param eventRequest the event request containing updated event details.
     * @return the updated event.
     */
    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable UUID eventId, @RequestBody EventRequest eventRequest) {
        try {
            Event updatedEvent = eventService.updateEvent(
                    eventId,
                    eventRequest.getEventName(),
                    eventRequest.getCreatedDate(),
                    eventRequest.getStartEvent(),
                    eventRequest.getEndEvent(),
                    eventRequest.getLocation(),
                    eventRequest.getDescription(),
                    eventRequest.getOrganizerId()
            );
            return ResponseEntity.ok(updatedEvent);
        } catch (EventNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Delete an event by its ID.
     *
     * @param eventId the UUID of the event to be deleted.
     * @return a response indicating the result of the deletion.
     */
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID eventId) {
        try {
            eventService.deleteEvent(eventId);
            return ResponseEntity.noContent().build();
        } catch (EventNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * Search for events based on various criteria.
     *
     * @param startDate the start date of the event.
     * @param endDate the end date of the event.
     * @param name the name of the event.
     * @param location the location of the event.
     * @return a list of events matching the search criteria.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Event>> searchEvents(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location) {
        List<Event> events = eventService.searchEvents(startDate, endDate, name, location);
        return ResponseEntity.ok(events);
    }
    /**
     * Retrieve all events with names similar to the specified name.
     *
     * @param eventName the name of the event to search for.
     * @return a list of events with names similar to the specified name.
     */
    @GetMapping("/searchName")
    public ResponseEntity<List<Event>> getAllLikeNames(@RequestParam(required = false) String eventName) {
        List<Event> events = eventName == null || eventName.isBlank()
                ? eventService.findAllEvents()
                : eventService.findAllLikeNames(eventName);
        return ResponseEntity.ok(events);
    }
}
