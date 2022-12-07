package ru.duremika.gaffer.requirement;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.duremika.gaffer.message.Message;

import java.util.Map;
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public interface Requirement {
    boolean check(Message message, Map<String, Object> args) throws Exception;
}
