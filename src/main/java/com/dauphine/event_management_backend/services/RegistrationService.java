package com.dauphine.event_management_backend.services;

import com.dauphine.event_management_backend.exceptions.EventNotFoundException;
import com.dauphine.event_management_backend.exceptions.RegistrationNotFoundException;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.Registration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
/**
 * Service interface for managing registrations to events.
 */
public interface RegistrationService {

    /**
     * Creates a registration for a user to an event.
     *
     * @param userId  the ID of the user to register
     * @param eventId the ID of the event to register to
     * @return the created registration
     * @throws UserNotFoundException  if the user is not found
     * @throws EventNotFoundException if the event is not found
     */    Registration createRegistration(UUID userId, UUID eventId) throws UserNotFoundException, EventNotFoundException;

    /**
     * Cancels a registration by its ID.
     *
     * @param registrationId the ID of the registration to cancel
     * @throws RegistrationNotFoundException if the registration is not found
     */
    void cancelRegistration(UUID registrationId) throws RegistrationNotFoundException;

    /**
     * Retrieves a registration by its ID.
     *
     * @param registrationId the ID of the registration to retrieve
     * @return an optional containing the found registration, or empty if not found
     */    Optional<Registration> getRegistrationById(UUID registrationId);

    /**
     * Retrieves all registrations for a specific event.
     *
     * @param eventId the ID of the event to retrieve registrations for
     * @return the list of registrations for the specified event
     */
    List<Registration> getRegistrationsByEventId(UUID eventId);

    /**
     * Retrieves all registrations of a specific user.
     *
     * @param userId the ID of the user to retrieve registrations for
     * @return the list of registrations made by the specified user
     */
    List<Registration> getRegistrationsByUserId(UUID userId);

    /**
     * Checks if a user is registered to a specific event.
     *
     * @param userId  the ID of the user to check registration for
     * @param eventId the ID of the event to check registration for
     * @return true if the user is registered to the event, false otherwise
     * @throws UserNotFoundException  if the user is not found
     * @throws EventNotFoundException if the event is not found
     */
    boolean isUserRegistered(UUID userId, UUID eventId) throws UserNotFoundException, EventNotFoundException;
}
