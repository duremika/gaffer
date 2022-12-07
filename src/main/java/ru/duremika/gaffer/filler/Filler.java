package ru.duremika.gaffer.filler;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.duremika.gaffer.message.Message;
import ru.duremika.gaffer.scenario.Scenario.Node.Field;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public interface Filler {
    boolean fill(Message message, Field field) throws Exception;
}
