package com.dauphine.event_management_backend.controller;

import com.dauphine.event_management_backend.dto.FeedbackRequest; // Assume this exists for adding/updating feedback
import com.dauphine.event_management_backend.exceptions.EventNotFoundException;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.Event;
import com.dauphine.event_management_backend.models.Feedback;
import com.dauphine.event_management_backend.models.User;
import com.dauphine.event_management_backend.services.EventService;
import com.dauphine.event_management_backend.services.FeedbackService;
import com.dauphine.event_management_backend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
/**
 * Controller for handling requests related to feedbacks.
 */
@RestController
@RequestMapping("/v1/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final EventService eventService;
    private final UserService userService;
    /**
     * Constructor to initialize services.
     *
     * @param feedbackService the feedback service to be used.
     * @param eventService the event service to be used.
     * @param userService the user service to be used.
     */
    public FeedbackController(FeedbackService feedbackService, EventService eventService, UserService userService) {
        this.feedbackService = feedbackService;
        this.userService = userService;
        this.eventService = eventService;
    }
    /**
     * Add new feedback.
     *
     * @param feedbackRequest the feedback request containing feedback details.
     * @return the created feedback.
     */
    @PostMapping
    public ResponseEntity<Feedback> addFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        System.out.println("FeedbackRequest TEST : " + feedbackRequest.getFeedbackUserId());
        System.out.println("FeedbackRequest TEST : " + feedbackRequest.getFeedbackEventId());
        System.out.println("FeedbackRequest TEST : " + feedbackRequest.getComment());
        System.out.println("FeedbackRequest TEST : " + feedbackRequest.getRating());

        try {
            Event event = eventService.findEventById(feedbackRequest.getFeedbackEventId());
            User user = userService.getUserById(feedbackRequest.getFeedbackUserId());

            Short rating = feedbackRequest.getRating() == null ? null : feedbackRequest.getRating().shortValue();

            // Appeler addFeedback avec les entités et le rating converti
            Feedback feedback = feedbackService.addFeedback(event, user, feedbackRequest.getComment(), rating);
            return new ResponseEntity<>(feedback, HttpStatus.CREATED);
        } catch (EventNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    /**
     * Retrieve feedback by its ID.
     *
     * @param feedbackId the UUID of the feedback to be retrieved.
     * @return the feedback with the specified ID.
     */
    @GetMapping("/{feedbackId}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable UUID feedbackId) {
        return feedbackService.getFeedbackById(feedbackId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /**
     * Retrieve feedbacks by event ID.
     *
     * @param eventId the UUID of the event.
     * @return a list of feedbacks for the specified event.
     */
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByEventId(@PathVariable UUID eventId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByEventId(eventId);
        return ResponseEntity.ok(feedbacks);
    }
    /**
     * Retrieve feedbacks by user ID.
     *
     * @param userId the UUID of the user.
     * @return a list of feedbacks from the specified user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByUserId(@PathVariable UUID userId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByUserId(userId);
        return ResponseEntity.ok(feedbacks);
    }
    /**
     * Update existing feedback.
     *
     * @param feedbackId the UUID of the feedback to be updated.
     * @param feedbackRequest the feedback request containing updated feedback details.
     * @return the updated feedback.
     */
    @PutMapping("/{feedbackId}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable UUID feedbackId, @RequestBody FeedbackRequest feedbackRequest) {
        return feedbackService.updateFeedback(feedbackId,
                        feedbackRequest.getComment(),
                        feedbackRequest.getRating())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /**
     * Delete feedback by its ID.
     *
     * @param feedbackId the UUID of the feedback to be deleted.
     * @return a response indicating the result of the deletion.
     */
    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable UUID feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.noContent().build();
    }
}
