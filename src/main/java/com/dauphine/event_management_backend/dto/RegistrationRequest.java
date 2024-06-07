package com.dauphine.event_management_backend.dto;

import java.time.LocalDateTime;

public class RegistrationRequest {
    private LocalDateTime registrationDate;

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}
