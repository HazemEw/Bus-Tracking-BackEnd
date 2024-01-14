package com.example.login.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NotificationStatus {
    ACCEPTED, REJECTED, IN_PROCESS;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
}
