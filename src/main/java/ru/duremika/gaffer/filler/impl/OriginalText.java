package ru.duremika.gaffer.filler.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.RequiredArgsConstructor;
import ru.duremika.gaffer.filler.Filler;
import ru.duremika.gaffer.message.Message;
import ru.duremika.gaffer.scenario.Scenario.Node.Field;

@RequiredArgsConstructor
@JsonTypeName("original_text")
public class OriginalText implements Filler {
    @Override
    public boolean fill(Message message, Field field) {

        return true;
    }
}
