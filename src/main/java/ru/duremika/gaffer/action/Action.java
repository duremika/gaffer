package ru.duremika.gaffer.action;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public interface Action {
    void run() throws Exception;
}
