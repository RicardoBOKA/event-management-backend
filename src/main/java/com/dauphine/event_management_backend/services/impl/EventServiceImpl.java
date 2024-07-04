package com.dauphine.event_management_backend.services.impl;

import com.dauphine.event_management_backend.exceptions.EventNotFoundException;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.Event;
import com.dauphine.event_management_backend.models.Registration;
import com.dauphine.event_management_backend.models.User;
import com.dauphine.event_management_backend.repository.EventRepository;
import com.dauphine.event_management_backend.repository.FeedbackRepository;
import com.dauphine.event_management_backend.repository.RegistrationRepository;
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
    private final RegistrationRepository registrationRepository;
    private final RegistrationServiceImpl registrationService;


    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, RegistrationRepository registrationRepository, RegistrationServiceImpl registrationService) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.registrationRepository = registrationRepository;
        this.registrationService = registrationService;
    }

    @Override
    public Event createEvent(String eventName, LocalDateTime createdDate, LocalDateTime startEvent,
                             LocalDateTime endEvent, Location location, String description, UUID organizerId) throws UserNotFoundException, EventNotFoundException {

        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(organizerId));
        System.out.println("Test ici 2");


        Event event = new Event();
        event.setEventId(UUID.randomUUID());
        event.setEventName(eventName);
        event.setCreatedDate(createdDate);
        event.setStartEvent(startEvent);
        event.setEndEvent(endEvent);
        event.setLocation(location.toString());
        event.setDescription(description);
        event.setUser(organizer);

        System.out.println("Organizer ID : " + event.getUser().getUserId());
        System.out.println("Event ID : " + event.getEventId());

        Event savedEvent = eventRepository.save(event);

        Registration registration = registrationService.createRegistration(organizerId, event.getEventId());

        registrationRepository.save(registration);

        return savedEvent;
    }

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
    public List<Event> searchEvents(LocalDateTime startDate, LocalDateTime endDate) {
        System.out.println("startDate : " + startDate);
        List<Event> filtrerEvents =  eventRepository.searchEvents(startDate, endDate);

        return  filtrerEvents;
    }

    @Override
    public List<Event> findAllLikeNames(String eventName) {
        System.out.println("Event name : " + eventName);
        return eventRepository.findAllLikeNames(eventName);
    }


}
