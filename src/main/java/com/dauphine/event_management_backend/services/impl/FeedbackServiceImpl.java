package com.dauphine.event_management_backend.services.impl;

import com.dauphine.event_management_backend.exceptions.EventNotFoundException;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.Event;
import com.dauphine.event_management_backend.models.Feedback;
import com.dauphine.event_management_backend.models.User;
import com.dauphine.event_management_backend.repository.EventRepository;
import com.dauphine.event_management_backend.repository.FeedbackRepository;
import com.dauphine.event_management_backend.repository.UserRepository;
import com.dauphine.event_management_backend.services.FeedbackService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }
    @Override
    public Feedback addFeedback(Event event, User user, String comment, Short rating) throws EventNotFoundException, UserNotFoundException {
        // Check if the event exists and is completed
        Event e = eventRepository.findById(event.getEventId())
                .orElseThrow(() -> new EventNotFoundException(event.getEventId()));
        if (event.getEndEvent().isAfter(java.time.LocalDateTime.now())) {
            throw new IllegalStateException("Feedback cannot be added before the event is completed.");
        }
        // Check if the user exists
        User u = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new UserNotFoundException(user.getUserId()));

        Feedback feedback = new Feedback(event, user, comment, rating);
        return feedbackRepository.save(feedback);
    }

    @Override
    public Optional<Feedback> getFeedbackById(UUID feedbackId) {
        return feedbackRepository.findById(feedbackId);
    }

    @Override
    public List<Feedback> getFeedbacksByEventId(UUID eventId) {
        return feedbackRepository.findFeedbacksByEventId(eventId);
    }

    @Override
    public List<Feedback> getFeedbacksByUserId(UUID userId) {
        return feedbackRepository.findFeedbacksByUserId(userId);
    }

    @Override
    public Optional<Feedback> updateFeedback(UUID feedbackId, String comment, Short rating) {
        return feedbackRepository.findById(feedbackId).map(existingFeedback -> {
            existingFeedback.setComment(comment);
            existingFeedback.setRating(rating);
            return feedbackRepository.save(existingFeedback);
        });
    }

    @Override
    public void deleteFeedback(UUID feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }
}
