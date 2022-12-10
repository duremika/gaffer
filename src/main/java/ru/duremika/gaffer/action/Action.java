package ru.duremika.gaffer.action;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.duremika.gaffer.message.Message;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public interface Action {
    Message run() throws Exception;
}
