package com.dauphine.event_management_backend.repository;

import com.dauphine.event_management_backend.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
    // Requête JPQL pour trouver tous les feedbacks associés à un événement spécifique
    @Query("SELECT f FROM Feedback f WHERE f.eventId = :eventId")
    List<Feedback> findFeedbacksByEventId(@Param("eventId") UUID eventId);

    // Requête JPQL pour trouver tous les feedbacks donnés par un utilisateur spécifique
    @Query("SELECT f FROM Feedback f WHERE f.userId = :userId")
    List<Feedback> findFeedbacksByUserId(@Param("userId") UUID userId);
}
