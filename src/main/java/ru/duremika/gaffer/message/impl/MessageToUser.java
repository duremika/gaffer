package ru.duremika.gaffer.message.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import ru.duremika.gaffer.message.Message;

@Data
@JsonTypeName("MESSAGE_TO_USER")
public class MessageToUser implements Message {
    @JsonProperty
    private final String text;
}
