package com.dauphine.event_management_backend.dto;

public class FeedbackRequest {
    private String comment;
    private Byte rating;

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
