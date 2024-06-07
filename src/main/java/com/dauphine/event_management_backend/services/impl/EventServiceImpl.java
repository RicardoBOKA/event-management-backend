package com.dauphine.event_management_backend.services.impl;

import com.dauphine.event_management_backend.exceptions.EventNotFoundException;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.Event;
import com.dauphine.event_management_backend.repository.EventRepository;
import com.dauphine.event_management_backend.repository.UserRepository;
import com.dauphine.event_management_backend.ressources.Location;
import com.dauphine.event_management_backend.services.EventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Event createEvent(Event event) throws UserNotFoundException {
        UUID organizerId = event.getOrganizerId();
        userRepository.findById(organizerId).orElseThrow(() -> new UserNotFoundException(organizerId));

        // Sauvegardez l'événement si l'organisateur est valide
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(UUID eventId, Event updatedEvent) throws EventNotFoundException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        event.setEventName(updatedEvent.getEventName());
        event.setCreatedDate(updatedEvent.getCreatedDate());
        event.setStartEvent(updatedEvent.getStartEvent());
        event.setEndEvent(updatedEvent.getEndEvent());
        event.setLocation(updatedEvent.getLocation());
        event.setDescription(updatedEvent.getDescription());
        event.setOrganizerId(updatedEvent.getOrganizerId());
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(UUID eventId) {
        eventRepository.deleteById(eventId);
    }

    @Override
    public Event findEventById(UUID eventId) throws EventNotFoundException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(UUID.randomUUID()));
        return event;
    }

    @Override
    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }



    @Override
    public List<Event> findEventsByOrganizer(UUID organizerId) throws UserNotFoundException {
        userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(organizerId));

        // Récupérez les événements organisés par cet utilisateur
        return eventRepository.findEventsByOrganizer(organizerId);
    }

    @Override
    public List<Event> searchEvents(LocalDateTime startDate, LocalDateTime endDate, String name, String location) {
        return eventRepository.searchEvents(startDate, endDate, name, location);
    }
}
