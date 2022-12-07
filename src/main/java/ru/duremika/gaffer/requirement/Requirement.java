package ru.duremika.gaffer.requirement;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.duremika.gaffer.message.Message;
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public interface Requirement {
    boolean check(Message message) throws Exception;
}
