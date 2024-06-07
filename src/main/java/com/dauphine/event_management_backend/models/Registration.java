package com.dauphine.event_management_backend.models;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

public class Registration {
    private UUID registrationid;
    private UUID userId;
    private UUID eventId;
    private LocalDateTime registrationDate;

    public UUID getRegistrationid() {
        return registrationid;
    }

    public void setRegistrationid(UUID registrationid) {
        this.registrationid = registrationid;
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

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}
