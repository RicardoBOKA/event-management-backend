package com.dauphine.event_management_backend.dto;

import java.util.UUID;

public class FeedbackRequest {
    private UUID feedbackUserId;
    private UUID feedbackEventID;
    private String comment;
    private Short rating;

    public UUID getFeedbackUserId() {
        return feedbackUserId;
    }

    public void setFeedbackUserId(UUID feedbackUserId) {
        this.feedbackUserId = feedbackUserId;
    }

    public UUID getFeedbackEventID() {
        return feedbackEventID;
    }

    public void setFeedbackEventID(UUID feedbackEventID) {
        this.feedbackEventID = feedbackEventID;
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
