package ru.duremika.core.requirement;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.duremika.core.dto.UserState;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public interface Requirement {
    boolean check(UserState userState) throws Exception;
}
