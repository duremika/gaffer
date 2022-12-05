package ru.duremika.gaffer.message.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import ru.duremika.gaffer.message.Message;

@Data
@JsonTypeName("ANSWER_TO_USER")
public class AnswerToUser implements Message {
    @JsonProperty
    private final String text;
}
