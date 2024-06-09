package com.dauphine.event_management_backend.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "feedbacks")
public class Feedback {
    @Id
    @Column(name = "feedback_id", updatable = false, nullable = false)
    private UUID feedbackid;

    // Relation Feedback to Event
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    private Event event;

    // Relation Feedback to User
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;
    @Column(name = "rating")
    private Short rating;

    public Feedback(Event event, User user, String comment, Short rating) {
        this.feedbackid = UUID.randomUUID();
        this.user = user;
        this.event = event;
        this.comment = comment;
        this.rating = rating;
    }

    public Feedback() {}

    public UUID getFeedbackid() {
        return feedbackid;
    }

    public void setFeedbackid(UUID feedbackid) {
        this.feedbackid = feedbackid;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Short  getRating() {
        return rating;
    }

    public void setRating(Short  rating) {
        this.rating = rating;
    }
}
