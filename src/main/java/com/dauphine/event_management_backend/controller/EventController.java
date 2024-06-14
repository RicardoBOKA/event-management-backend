package com.dauphine.event_management_backend.controller;


import com.dauphine.event_management_backend.dto.EventRequest;
import com.dauphine.event_management_backend.exceptions.EventNotFoundException;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.Event;
import com.dauphine.event_management_backend.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.findAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable UUID eventId) {
        try {
            Event event = eventService.findEventById(eventId);
            return ResponseEntity.ok(event);
        } catch (EventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

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


    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID eventId) {
        try {
            eventService.deleteEvent(eventId);
            return ResponseEntity.noContent().build();
        } catch (EventNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
