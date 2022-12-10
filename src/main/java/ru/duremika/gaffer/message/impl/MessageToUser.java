package ru.duremika.gaffer.message.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.RequiredArgsConstructor;
import ru.duremika.gaffer.message.Message;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@JsonTypeName("MESSAGE_TO_USER")
public class MessageToUser extends Message {
    @JsonProperty
    private final String text;

    @JsonProperty
    private final List<List<String>> buttons;

    @Override
    public String toString() {
        return text + (buttons == null || buttons.isEmpty() ?
                "" :
                "\n\n" + buttons.stream()
                        .map(buttonsRow -> buttonsRow.stream()
                                .map(button -> "[  " + button + "  ]")
                                .collect(Collectors.joining(" | ")))
                        .collect(Collectors.joining("\n")));
    }
}
