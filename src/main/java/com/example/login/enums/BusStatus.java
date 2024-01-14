package com.example.login.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BusStatus {
    NOT_ACTIVE,
    ON_STATION,
    IN_GO_WAY,
    IN_BACK_WAY,
    ACTIVE;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
    }