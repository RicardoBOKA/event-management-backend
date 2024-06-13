package com.dauphine.event_management_backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "registrations")
public class Registration {
    @Id
    @Column(name = "registration_id", nullable = false)
    private UUID registrationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    public UUID getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(UUID registrationid) {
        this.registrationId = registrationid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}
