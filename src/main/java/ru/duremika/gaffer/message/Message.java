package ru.duremika.gaffer.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "message_name")
public abstract class Message {
    @Getter
    @JsonProperty("user_id")
    private String userId;
}
