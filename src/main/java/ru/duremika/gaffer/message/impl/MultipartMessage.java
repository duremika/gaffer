package ru.duremika.gaffer.message.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.RequiredArgsConstructor;
import ru.duremika.gaffer.message.Message;

import java.util.List;

@RequiredArgsConstructor
@JsonTypeName("MULTIPART_MESSAGE")
public class MultipartMessage extends Message {
    @JsonProperty("messages")
    private final List<Message> messageList;
}
