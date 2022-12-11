package ru.duremika.core.action;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.duremika.core.message.Message;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public interface Action {
    Message run() throws Exception;
}
