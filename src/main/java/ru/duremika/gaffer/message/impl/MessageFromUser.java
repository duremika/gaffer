package ru.duremika.gaffer.message.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.duremika.gaffer.message.Message;

@Getter
@AllArgsConstructor
@JsonTypeName("MESSAGE_FROM_USER")
public class MessageFromUser extends Message {
    @JsonProperty
    private String text;

    @JsonProperty("user_id")
    private String userId;
}
