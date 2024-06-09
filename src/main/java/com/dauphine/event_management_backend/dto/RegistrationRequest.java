package com.dauphine.event_management_backend.dto;

import com.dauphine.event_management_backend.models.Event;
import com.dauphine.event_management_backend.models.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrationRequest {
    private UUID registrationUserId;
    private UUID registrationEventId;

    public UUID getRegistrationUserId() {
        return registrationUserId;
    }

    public void setRegistrationUserId(UUID registrationUserId) {
        this.registrationUserId = registrationUserId;
    }

    public UUID getRegistrationEventId() {
        return registrationEventId;
    }

    public void setRegistrationEventId(UUID registrationEventId) {
        this.registrationEventId = registrationEventId;
    }

    private LocalDateTime registrationDate;

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}
