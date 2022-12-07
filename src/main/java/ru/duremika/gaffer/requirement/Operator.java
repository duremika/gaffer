package ru.duremika.gaffer.requirement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

public class Operator {
    @JsonProperty
    Type type;

    @JsonProperty
    String value;

    @RequiredArgsConstructor
    enum Type {
        EXISTS("exists"),
        EQUAL("equal"),
        NOT_EQUAL("not_equal"),
        MORE("more"),
        MORE_OR_EQUAL("more_or_equal"),
        LESS("less"),
        LESS_OR_EQUAL("less_or_equal");

        @JsonValue
        private final String type;
    }
}
