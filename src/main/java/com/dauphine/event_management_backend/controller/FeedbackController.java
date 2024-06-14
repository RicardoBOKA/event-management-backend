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

@RestController
@RequestMapping("/v1/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final EventService eventService;
    private final UserService userService;

    public FeedbackController(FeedbackService feedbackService, EventService eventService, UserService userService) {
        this.feedbackService = feedbackService;
        this.userService = userService;
        this.eventService = eventService;
    }

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

    @GetMapping("/{feedbackId}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable UUID feedbackId) {
        return feedbackService.getFeedbackById(feedbackId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByEventId(@PathVariable UUID eventId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByEventId(eventId);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByUserId(@PathVariable UUID userId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByUserId(userId);
        return ResponseEntity.ok(feedbacks);
    }

    @PutMapping("/{feedbackId}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable UUID feedbackId, @RequestBody FeedbackRequest feedbackRequest) {
        return feedbackService.updateFeedback(feedbackId,
                        feedbackRequest.getComment(),
                        feedbackRequest.getRating())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable UUID feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.noContent().build();
    }
}
