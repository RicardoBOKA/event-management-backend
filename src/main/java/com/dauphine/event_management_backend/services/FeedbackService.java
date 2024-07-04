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
/**
 * Service interface for managing feedbacks.
 */
public interface FeedbackService {
    /**
     * Adds a feedback for a specific event.
     *
     * @param event   the event to add feedback to
     * @param user    the user providing the feedback
     * @param comment the comment provided in the feedback
     * @param rating  the rating (optional) given in the feedback
     * @return the added feedback
     * @throws EventNotFoundException if the event is not found
     * @throws UserNotFoundException  if the user is not found
     */    Feedback addFeedback(Event event, User user, String comment, Short rating) throws EventNotFoundException, UserNotFoundException;
    /**
     * Retrieves a feedback by its ID.
     *
     * @param feedbackId the ID of the feedback to retrieve
     * @return an optional containing the found feedback, or empty if not found
     */
    Optional<Feedback> getFeedbackById(UUID feedbackId);

    /**
     * Retrieves all feedbacks for a specific event.
     *
     * @param eventId the ID of the event to retrieve feedbacks for
     * @return the list of feedbacks for the specified event
     */
    List<Feedback> getFeedbacksByEventId(UUID eventId);

    /**
     * Retrieves all feedbacks given by a specific user.
     *
     * @param userId the ID of the user to retrieve feedbacks from
     * @return the list of feedbacks given by the specified user
     */
    List<Feedback> getFeedbacksByUserId(UUID userId);

    /**
     * Updates an existing feedback.
     *
     * @param feedbackId the ID of the feedback to update
     * @param comment    the new comment for the feedback
     * @param rating     the new rating for the feedback (optional)
     * @return an optional containing the updated feedback, or empty if not found
     */
    Optional<Feedback> updateFeedback(UUID feedbackId, String comment, Short rating);

    /**
     * Deletes a feedback by its ID.
     *
     * @param feedbackId the ID of the feedback to delete
     */
    void deleteFeedback(UUID feedbackId);
}
