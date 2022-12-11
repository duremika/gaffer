package ru.duremika.gaffer.message.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.duremika.gaffer.message.Message;

@JsonTypeName("ERROR_MESSAGE")
public class ErrorMessage extends Message {
    @JsonProperty("error_code")
    private int errorCode;

    @JsonProperty
    private String description;

    public ErrorMessage(String userId, int errorCode, String description) {
        setUserId(userId);
        this.errorCode = errorCode;
        this.description = description;
    }
}
