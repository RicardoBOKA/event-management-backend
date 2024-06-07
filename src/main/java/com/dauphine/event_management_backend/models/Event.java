package com.dauphine.event_management_backend.models;

import com.dauphine.event_management_backend.ressources.Location;

import java.time.LocalDateTime;
import java.util.UUID;

public class Event {
    private UUID eventId;
    private String eventName;
    private LocalDateTime createdDate;
    private LocalDateTime startEvent;
    private LocalDateTime endEvent;
    private Location location;
    private String description;
    private UUID organizerId;

    public UUID getEventId() {
        return eventId;
    }

    public LocalDateTime getStartEvent() {
        return startEvent;
    }

    public void setStartEvent(LocalDateTime startEvent) {
        this.startEvent = startEvent;
    }

    public LocalDateTime getEndEvent() {
        return endEvent;
    }

    public void setEndEvent(LocalDateTime endEvent) {
        this.endEvent = endEvent;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(UUID organizerId) {
        this.organizerId = organizerId;
    }
}
