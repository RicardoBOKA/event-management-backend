package com.dauphine.event_management_backend.services;

import com.dauphine.event_management_backend.exceptions.EventNotFoundException;
import com.dauphine.event_management_backend.exceptions.RegistrationNotFoundException;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.Registration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RegistrationService {

    // Enregistrer un utilisateur à un événement
    Registration createRegistration(UUID userId, UUID eventId) throws UserNotFoundException, EventNotFoundException;

    // Annuler une inscription
    void cancelRegistration(UUID registrationId) throws RegistrationNotFoundException;

    // Récupérer une inscription par son ID
    Optional<Registration> getRegistrationById(UUID registrationId);

    // Récupérer toutes les inscriptions pour un événement donné
    List<Registration> getRegistrationsByEventId(UUID eventId);

    // Récupérer toutes les inscriptions d'un utilisateur
    List<Registration> getRegistrationsByUserId(UUID userId);

    // Vérifier si un utilisateur est inscrit à un événement spécifique
    boolean isUserRegistered(UUID userId, UUID eventId) throws UserNotFoundException, EventNotFoundException;
}
