package com.dauphine.event_management_backend.controller;

import com.dauphine.event_management_backend.dto.RegistrationRequest;
import com.dauphine.event_management_backend.exceptions.EventNotFoundException;
import com.dauphine.event_management_backend.exceptions.RegistrationNotFoundException;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.Registration;
import com.dauphine.event_management_backend.services.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
/**
 * Controller for handling requests related to registrations.
 */
@RestController
@RequestMapping("/v1/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;
    /**
     * Constructor to initialize the registration service.
     *
     * @param registrationService the registration service to be used.
     */
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
    /**
     * Create a new registration.
     *
     * @param registrationRequest the registration request containing user and event IDs.
     * @return the created registration.
     */
    @PostMapping
    public ResponseEntity<Registration> createRegistration(@RequestBody RegistrationRequest registrationRequest) {
        try {
            Registration registration = registrationService.createRegistration(
                    registrationRequest.getRegistrationUserId(),
                    registrationRequest.getRegistrationEventId()
            );
            return ResponseEntity.ok(registration);
        } catch (UserNotFoundException | EventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    /**
     * Cancel an existing registration.
     *
     * @param registrationId the UUID of the registration to be cancelled.
     * @return a response indicating the result of the cancellation.
     * @throws RegistrationNotFoundException if the registration is not found.
     */
    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> cancelRegistration(@PathVariable UUID registrationId) throws RegistrationNotFoundException {

            Registration registration = registrationService.getRegistrationById(registrationId)
                    .orElseThrow(() -> new RegistrationNotFoundException(registrationId));

            registrationService.cancelRegistration(registrationId);
            return ResponseEntity.noContent().build();

    }
    /**
     * Retrieve a registration by its ID.
     *
     * @param registrationId the UUID of the registration to be retrieved.
     * @return the registration with the specified ID.
     */
    @GetMapping("/{registrationId}")
    public ResponseEntity<Registration> getRegistrationById(@PathVariable UUID registrationId) {
        return registrationService.getRegistrationById(registrationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /**
     * Retrieve registrations by event ID.
     *
     * @param eventId the UUID of the event.
     * @return a list of registrations for the specified event.
     */
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Registration>> getRegistrationsByEventId(@PathVariable UUID eventId) {
        try {
            List<Registration> registrations = registrationService.getRegistrationsByEventId(eventId);
            if (registrations.isEmpty()) {
                throw new EventNotFoundException(eventId);
            }
            return ResponseEntity.ok(registrations);
        } catch (EventNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * Retrieve registrations by user ID.
     *
     * @param userId the UUID of the user.
     * @return a list of registrations for the specified user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Registration>> getRegistrationsByUserId(@PathVariable UUID userId) {
        try {
            List<Registration> registrations = registrationService.getRegistrationsByUserId(userId);
            if (registrations.isEmpty()) {
                throw new UserNotFoundException(userId);
            }
            return ResponseEntity.ok(registrations);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * Check if a user is registered for an event.
     *
     * @param userId the UUID of the user.
     * @param eventId the UUID of the event.
     * @return a boolean indicating whether the user is registered for the event.
     */
    @GetMapping("/check")
    public ResponseEntity<Boolean> isUserRegistered(@RequestParam UUID userId, @RequestParam UUID eventId) {
        try {
            boolean isRegistered = registrationService.isUserRegistered(userId, eventId);
            return ResponseEntity.ok(isRegistered);
        } catch (EventNotFoundException | UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
