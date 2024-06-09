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

@RestController
@RequestMapping("/v1/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

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

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> cancelRegistration(@PathVariable UUID registrationId) {
        try {
            Registration registration = registrationService.getRegistrationById(registrationId)
                    .orElseThrow(() -> new RegistrationNotFoundException(registrationId));

            registrationService.cancelRegistration(registrationId);
            return ResponseEntity.noContent().build();

        } catch (RegistrationNotFoundException e) {
            // Renvoie une réponse 404 Not Found si l'inscription n'est pas trouvée
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/{registrationId}")
    public ResponseEntity<Registration> getRegistrationById(@PathVariable UUID registrationId) {
        return registrationService.getRegistrationById(registrationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

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
