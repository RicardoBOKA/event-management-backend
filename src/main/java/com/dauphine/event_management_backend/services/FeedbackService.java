package com.dauphine.event_management_backend.services;

import com.dauphine.event_management_backend.exceptions.EventNotFoundException;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.Event;
import com.dauphine.event_management_backend.models.Feedback;
import com.dauphine.event_management_backend.models.User;
import org.springframework.data.repository.core.support.EventPublishingRepositoryProxyPostProcessor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeedbackService {
    // Ajouter un feedback pour un événement
    Feedback addFeedback(Event event, User user, String comment, Short rating) throws EventNotFoundException, UserNotFoundException;

    // Récupérer un feedback par son ID
    Optional<Feedback> getFeedbackById(UUID feedbackId);

    // Récupérer tous les feedbacks pour un événement spécifique
    List<Feedback> getFeedbacksByEventId(UUID eventId);

    // Récupérer tous les feedbacks donnés par un utilisateur
    List<Feedback> getFeedbacksByUserId(UUID userId);

    // Mettre à jour un feedback existant
    Optional<Feedback> updateFeedback(UUID feedbackId, String comment, Short rating);

    // Supprimer un feedback
    void deleteFeedback(UUID feedbackId);
}
