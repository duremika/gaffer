package ru.duremika.core.message.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.duremika.core.message.Message;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@ToString
@JsonTypeName("MESSAGE_TO_USER")
public class MessageToUser extends Message {
    @JsonProperty
    private final String text;

    @JsonProperty
    private final List<List<String>> buttons;
}
