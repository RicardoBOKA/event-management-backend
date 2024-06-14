package com.dauphine.event_management_backend.dto;

import java.util.UUID;

public class FeedbackRequest {
    private UUID feedbackUserId;
    private UUID feedbackEventId;
    private String comment;
    private Short rating;

    public UUID getFeedbackUserId() {
        return feedbackUserId;
    }

    public void setFeedbackUserId(UUID feedbackUserId) {
        this.feedbackUserId = feedbackUserId;
    }

    public UUID getFeedbackEventId() {
        return feedbackEventId;
    }

    public void setFeedbackEventId(UUID feedbackEventId) {
        this.feedbackEventId = feedbackEventId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }
}
