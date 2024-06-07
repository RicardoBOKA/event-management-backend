package com.dauphine.event_management_backend.models;

import java.util.UUID;

public class Feedback {
    private UUID feedbackid;
    private UUID userId;
    private UUID eventId;

    private String comment;
    private Byte rating;
    public UUID getFeedbackid() {
        return feedbackid;
    }

    public void setFeedbackid(UUID feedbackid) {
        this.feedbackid = feedbackid;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Byte getRating() {
        return rating;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }
}
