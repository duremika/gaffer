package ru.duremika.gaffer.message.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import ru.duremika.gaffer.message.Message;

@Getter
@JsonTypeName("MESSAGE_FROM_USER")
public class MessageFromUser extends Message {
    @JsonProperty
    private String text;

    public MessageFromUser(String userId, String text) {
        setUserId(userId);
        this.text = text;
    }
}
