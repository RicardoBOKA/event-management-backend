package com.dauphine.event_management_backend.services;

import com.dauphine.event_management_backend.exceptions.EventNotFoundException;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.Feedback;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeedbackService {
    // Ajouter un feedback pour un événement
    Feedback addFeedback(Feedback feedback) throws EventNotFoundException, UserNotFoundException;

    // Récupérer un feedback par son ID
    Optional<Feedback> getFeedbackById(UUID feedbackId);

    // Récupérer tous les feedbacks pour un événement spécifique
    List<Feedback> getFeedbacksByEventId(UUID eventId);

    // Récupérer tous les feedbacks donnés par un utilisateur
    List<Feedback> getFeedbacksByUserId(UUID userId);

    // Mettre à jour un feedback existant
    Optional<Feedback> updateFeedback(UUID feedbackId, Feedback feedback);

    // Supprimer un feedback
    void deleteFeedback(UUID feedbackId);
}
