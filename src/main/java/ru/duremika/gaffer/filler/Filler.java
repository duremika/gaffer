package ru.duremika.gaffer.filler;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.duremika.gaffer.dto.UserState;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public interface Filler {
    Object fill(UserState userState) throws Exception;
}
