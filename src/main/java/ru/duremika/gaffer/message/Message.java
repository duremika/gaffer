package ru.duremika.gaffer.message;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "message_name")
public interface Message {
}
