package ru.duremika.gaffer.message.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import ru.duremika.gaffer.message.Message;

@Data
@JsonTypeName("MESSAGE_FROM_USER")
public class MessageFromUser implements Message {
    @JsonProperty
    private String text;
}
