package com.dauphine.event_management_backend.services.impl;

import com.dauphine.event_management_backend.exceptions.EventNotFoundException;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.Event;
import com.dauphine.event_management_backend.models.Registration;
import com.dauphine.event_management_backend.models.User;
import com.dauphine.event_management_backend.repository.EventRepository;
import com.dauphine.event_management_backend.repository.RegistrationRepository;
import com.dauphine.event_management_backend.repository.UserRepository;
import com.dauphine.event_management_backend.services.RegistrationService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Registration createRegistration(UUID userId, UUID eventId) throws UserNotFoundException, EventNotFoundException {
        // Check if the user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Check if the event exists
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        // Create the registration
        Registration registration = new Registration();
        registration.setRegistrationid(UUID.randomUUID());
        registration.setUserId(userId);
        registration.setEventId(eventId);
        registration.setRegistrationDate(java.time.LocalDateTime.now());
        return registrationRepository.save(registration);
    }


    @Override
    public void cancelRegistration(UUID registrationId) {
        registrationRepository.deleteById(registrationId);
    }

    @Override
    public Optional<Registration> getRegistrationById(UUID registrationId) {
        return registrationRepository.findById(registrationId);
    }

    @Override
    public List<Registration> getRegistrationsByEventId(UUID eventId) {
        return registrationRepository.getRegistrationsByEventId(eventId);
    }

    @Override
    public List<Registration> getRegistrationsByUserId(UUID userId) {
        return registrationRepository.getRegistrationsByUserId(userId);
    }

    @Override
    public boolean isUserRegistered(UUID userId, UUID eventId) {
        return registrationRepository.isUserRegistered(userId, eventId);
    }
}
