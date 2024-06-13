package com.dauphine.event_management_backend.services.impl;

import com.dauphine.event_management_backend.exceptions.EventNotFoundException;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.Event;
import com.dauphine.event_management_backend.models.User;
import com.dauphine.event_management_backend.repository.EventRepository;
import com.dauphine.event_management_backend.repository.UserRepository;
import com.dauphine.event_management_backend.ressources.Location;
import com.dauphine.event_management_backend.services.EventService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    //@Override
    //public Event createEvent(Event event) throws UserNotFoundException {
    //    UUID organizerId = event.getUser().getUserId();
    //    userRepository.findById(organizerId).orElseThrow(() -> new UserNotFoundException(organizerId));

        // Sauvegardez l'événement si l'organisateur est valide
    //    return eventRepository.save(event);
    //}

    @Override
    public Event createEvent(String eventName, LocalDateTime createdDate, LocalDateTime startEvent,
                             LocalDateTime endEvent, Location location, String description, UUID organizerId) throws UserNotFoundException {
        System.out.println("Test ici 1");
        System.out.println("organizerId : " + organizerId);
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(organizerId));
        System.out.println("Test ici 2");


        Event event = new Event();
        event.setEventId(UUID.randomUUID());
        System.out.println("Test ici 3");

        event.setEventName(eventName);
        System.out.println("Test ici 4");

        event.setCreatedDate(createdDate);
        System.out.println("Test ici 5");

        event.setStartEvent(startEvent);
        System.out.println("Test ici 6");

        event.setEndEvent(endEvent);
        System.out.println("Test ici 7");

        event.setLocation(location.toString());
        System.out.println("Test ici 8");

        event.setDescription(description);
        System.out.println("Test ici 9");

        event.setUser(organizer);
        System.out.println("Organizer ID : " + event.getUser().getUserId());
        System.out.println("Event ID : " + event.getEventId());
        return eventRepository.save(event);
    }

    //@Override
    //public Event updateEvent(UUID eventId, Event updatedEvent) throws EventNotFoundException {
    //    Event event = eventRepository.findById(eventId)
    //            .orElseThrow(() -> new EventNotFoundException(eventId));

    //    event.setEventName(updatedEvent.getEventName());
    //    event.setCreatedDate(updatedEvent.getCreatedDate());
    //    event.setStartEvent(updatedEvent.getStartEvent());
    //    event.setEndEvent(updatedEvent.getEndEvent());
    //    event.setLocation(updatedEvent.getLocation());
    //    event.setDescription(updatedEvent.getDescription());
    //    event.setUser(updatedEvent.getUser());
    //    return eventRepository.save(event);
    //}

    @Override
    public Event updateEvent(UUID eventId, String eventName, LocalDateTime createdDate, LocalDateTime startEvent,
                             LocalDateTime endEvent, Location location, String description, UUID organizerId) throws EventNotFoundException, UserNotFoundException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        event.setEventName(eventName);
        event.setCreatedDate(createdDate);
        event.setStartEvent(startEvent);
        event.setEndEvent(endEvent);
        event.setLocation(location.toString());
        event.setDescription(description);

        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(organizerId));
        event.setUser(organizer);

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
