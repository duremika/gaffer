package ru.duremika.gaffer.requirement.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.duremika.gaffer.dto.UserState;
import ru.duremika.gaffer.requirement.Requirement;

import java.util.List;

@JsonTypeName("and")
public class And implements Requirement {
    @JsonProperty
    List<Requirement> requirements;

    @Override
    public boolean check(UserState userState) throws Exception {
        for (Requirement requirement : requirements) {
            if (!requirement.check(userState)) {
                return false;
            }
        }
        return true;
    }
}
