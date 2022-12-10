package ru.duremika.gaffer.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "message_name")
public abstract class Message {
    @Getter
    @Setter
    @JsonProperty("user_id")
    private String userId;
}
