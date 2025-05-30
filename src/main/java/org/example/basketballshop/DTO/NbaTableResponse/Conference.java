package org.example.basketballshop.DTO.NbaTableResponse;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Conference {
    EASTERN("Eastern"),
    WESTERN("Western");

    private final String value;

    Conference(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Conference fromString(String value) {
        for (Conference c : Conference.values()) {
            if (c.value.equalsIgnoreCase(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown conference: " + value);
    }
}
