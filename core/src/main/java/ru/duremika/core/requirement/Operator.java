package ru.duremika.core.requirement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class Operator {
    @JsonProperty
    Type type;

    @JsonProperty
    String value;

    @RequiredArgsConstructor
    public enum Type {
        EXISTS("exists"),
        NOT_EXISTS("not_exists"),
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
